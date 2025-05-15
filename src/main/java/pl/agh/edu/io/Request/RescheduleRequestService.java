package pl.agh.edu.io.Request;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Class.ClassSessionNotFoundException;
import pl.agh.edu.io.Class.ClassSessionRepository;
import pl.agh.edu.io.Class.ClassSessionService;
import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Course.CourseRepository;
import pl.agh.edu.io.User.User;
import pl.agh.edu.io.User.UserRole;
import pl.agh.edu.io.User.UserService;

import java.util.List;

@Service
public class RescheduleRequestService {
    private final RescheduleRequestRepository rescheduleRequestRepository;
    private final ClassSessionRepository classSessionRepository;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;
    private final ClassroomService classroomService;
    private final ClassSessionService classSessionService;
    private final CourseRepository courseRepository;

    public RescheduleRequestService(RescheduleRequestRepository rescheduleRequestRepository, ClassSessionRepository classSessionRepository, ClassroomRepository classroomRepository, UserService userService, ClassroomService classroomService, ClassSessionService classSessionService, CourseRepository courseRepository) {
        this.rescheduleRequestRepository = rescheduleRequestRepository;
        this.classSessionRepository = classSessionRepository;
        this.classroomRepository = classroomRepository;
        this.userService = userService;
        this.classroomService = classroomService;
        this.classSessionService = classSessionService;
        this.courseRepository = courseRepository;
    }

    //Prośba o jednokrotną zmianę terminu zajęć
    @Transactional
    public void createOneTimeRequest(RescheduleRequestDto rescheduleRequestDto, long userId) {
        User lecturer = userService.getUserEntityById(userId);

        if (lecturer.getRole() != UserRole.PROWADZACY) {
            throw new RuntimeException("This user is not a lecturer");
        }

        ClassSession session = classSessionRepository.findById(rescheduleRequestDto.classSessionDto().id())
                .orElseThrow(() -> new ClassSessionNotFoundException(rescheduleRequestDto.classSessionDto().id()));

        if (rescheduleRequestDto.classSessionDto().lecturer().id() != lecturer.getId()) {
            throw new RuntimeException("Unauthorized: not the session's teacher");
        }

        ClassroomDto classroomDto = rescheduleRequestDto.newClassroom();
        Classroom newClassroom = classroomRepository
                .findByBuildingAndNumber(classroomDto.building(), classroomDto.number())
                .orElseThrow(() -> new ClassroomNotFoundException(classroomDto.building(), classroomDto.number()));


        List<ClassSession> sessionsAtSameTime = classSessionRepository.findSessionsByClassroomAndTime(newClassroom.getId(), rescheduleRequestDto.newDateTime());
        if (!sessionsAtSameTime.isEmpty()) {
            throw new RuntimeException("Classroom is not available at the selected time");
        }

        RescheduleRequest request = new RescheduleRequest(
                session,
                rescheduleRequestDto.newDateTime(),
                newClassroom,
                rescheduleRequestDto.newDuration(),
                RequestStatus.PENDING,
                lecturer,
                session.getCourse().getStudentRep(),
                rescheduleRequestDto.requesterId(),
                false
        );

        rescheduleRequestRepository.save(request);
    }

//    @Transactional
//    public void createMultipleRequest(RescheduleRequestDto rescheduleRequestDto, long userId) {
//        User lecturer = userService.getUserEntityById(userId);
//
//        if (lecturer.getRole() != UserRole.PROWADZACY) {
//            throw new RuntimeException("This user is not a lecturer");
//        }
//
//        ClassSession session = classSessionRepository.findById(rescheduleRequestDto.classSessionDto().id())
//                .orElseThrow(() -> new ClassSessionNotFoundException(rescheduleRequestDto.classSessionDto().id()));
//
//        if (rescheduleRequestDto.classSessionDto().lecturer().id() != lecturer.getId()) {
//            throw new RuntimeException("Unauthorized: not the session's teacher");
//        }
//
//        ClassroomDto classroomDto = rescheduleRequestDto.newClassroom();
//        Classroom newClassroom = classroomRepository
//                .findByBuildingAndNumber(classroomDto.building(), classroomDto.number())
//                .orElseThrow(() -> new ClassroomNotFoundException(classroomDto.building(), classroomDto.number()));
//
//
//        List<ClassSession> sessionsAtSameTime = classSessionRepository.findSessionsByClassroomAndTime(newClassroom.getId(), rescheduleRequestDto.newDateTime());
//        if (!sessionsAtSameTime.isEmpty()) {
//            throw new RuntimeException("Classroom is not available at the selected time");
//        }
//
//        boolean isForAllSession = rescheduleRequestDto.isForAllSessions();
//
//        RescheduleRequest request = new RescheduleRequest(
//                session,
//                rescheduleRequestDto.newDateTime(),
//                newClassroom,
//                RequestStatus.PENDING,
//                lecturer,
//                session.getCourse().getStudentRep(),
//                isForAllSession
//        );
//    }

    public List<RescheduleRequestDto> getCompletedUserRequests(long userId) {
        return rescheduleRequestRepository.findAll().stream()
                .filter(request -> request.getRequesterId() == userId)
                .filter(request -> request.getStatus().equals(RequestStatus.REJECTED) || request.getStatus().equals(RequestStatus.ACCEPTED))
                .map(this::convertToDto)
                .toList();
    }

    public List<RescheduleRequestDto> getPendingRequests(long userId) {
        User user = userService.getUserEntityById(userId);

        return rescheduleRequestRepository.findAll().stream()
                .filter(request -> request.getStatus() == RequestStatus.PENDING
                        && (request.getClassRep().equals(user) || request.getLecturer().equals(user)))
                .map(this::convertToDto).toList();
    }

    @Transactional
    public void acceptRequest(Long requestId) {
        RescheduleRequest request = rescheduleRequestRepository.findById(requestId)
                .orElseThrow(() -> new RescheduleRequestNotFoundException(requestId));

        if (request.getStatus() != RequestStatus.PENDING)
            throw new RuntimeException("Already processed");


        ClassSession session = request.getClassSession();

        request.setOldTime(session.getDateTime());
        request.setOldDuration(session.getDuration());
        request.setOldClassroom(session.getClassroom());

        session.setDateTime(request.getNewDateTime());
        session.setClassroom(request.getNewClassroom());
        session.setDuration(request.getNewDuration());
        classSessionRepository.save(session);

        request.setStatus(RequestStatus.ACCEPTED);
        rescheduleRequestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        RescheduleRequest request = rescheduleRequestRepository.findById(requestId)
                .orElseThrow(() -> new RescheduleRequestNotFoundException(requestId));

        if (request.getStatus() != RequestStatus.PENDING)
            throw new RuntimeException("Already processed");

        request.setStatus(RequestStatus.REJECTED);
        rescheduleRequestRepository.save(request);
    }

    public void deleteById(Long requestId) {
        rescheduleRequestRepository.deleteById(requestId);
    }

    public RescheduleRequestDto convertToDto(RescheduleRequest request) {
        return new RescheduleRequestDto(
                request.getId(),
                classSessionService.convertToDto(request.getClassSession()),
                request.getRequesterId(),
                classroomService.convertToDto(request.getNewClassroom()),
                request.getNewDateTime(),
                request.getNewDuration(),
                request.getOldClassroom() != null ? classroomService.convertToDto(request.getOldClassroom()) : null,
                request.getOldTime(),
                request.getOldDuration(),
                request.getStatus(),
                request.isForAllSessions()
        );
    }
}
