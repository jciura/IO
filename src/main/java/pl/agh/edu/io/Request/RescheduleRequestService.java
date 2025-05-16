package pl.agh.edu.io.Request;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.agh.edu.io.Class.*;
import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Course.Course;
import pl.agh.edu.io.Course.CourseNotFoundException;
import pl.agh.edu.io.Course.CourseRepository;
import pl.agh.edu.io.User.User;
import pl.agh.edu.io.User.UserRole;
import pl.agh.edu.io.User.UserService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private LocalDateTime calculateNewDateTime(LocalDateTime currentDateTime, LocalDateTime newDateTime) {
        int newDayOfWeek = newDateTime.getDayOfWeek().getValue();
        LocalTime newTime = newDateTime.toLocalTime();
        int currentDayOfWeek = currentDateTime.getDayOfWeek().getValue();

        int daysDifference = (currentDayOfWeek - newDayOfWeek + 7) % 7;
        LocalDateTime adjustedDateTime = (daysDifference == 0) ? currentDateTime : currentDateTime.minusDays(daysDifference);

        return adjustedDateTime.withHour(newTime.getHour())
                .withMinute(newTime.getMinute())
                .withSecond(0)
                .withNano(0);
    }

    //Nie obsługuje sytuacji z zamianą dnia np. jeżeli zajęcia są w piątek przesuwamy na czwartek, to jeżeli konkretne spotkanie
    //jest w środę (bo zamiana dnia) to przesunie na wtorek
    @Transactional
    public void createRequest(RescheduleRequestDto requestDto, long userId) {
        User lecturer = userService.getUserEntityById(userId);

        if (lecturer.getRole() != UserRole.PROWADZACY) {
            throw new RuntimeException("This user is not a lecturer");
        }

        ClassroomDto classroomDto = requestDto.newClassroom();
        Classroom newClassroom = classroomRepository
                .findByBuildingAndNumber(classroomDto.building(), classroomDto.number())
                .orElseThrow(() -> new ClassroomNotFoundException(classroomDto.building(), classroomDto.number()));


        if (requestDto.isForAllSessions()) {
            Course course = courseRepository.findByName(requestDto.classSessionDto().courseName())
                    .orElseThrow(() -> new CourseNotFoundException(requestDto.classSessionDto().courseName()));

            List<ClassSession> allLecturerSessions = classSessionRepository.findAllByCourseId(course.getId());

            List<ClassSession> sessionsToReschedule = allLecturerSessions.stream()
                    .filter(session -> session.getDateTime().isAfter(LocalDateTime.now()))
                    .toList();

            if (sessionsToReschedule.isEmpty()) {
                throw new RuntimeException("No future sessions to reschedule");
            }

            LocalDateTime newDateTimeTemplate = requestDto.newDateTime();

            for (ClassSession session : sessionsToReschedule) {
                LocalDateTime newDateTime = calculateNewDateTime(session.getDateTime(), newDateTimeTemplate);

                boolean isAvailable = classSessionRepository
                        .findSessionsByClassroomAndTime(newClassroom.getId(), newDateTime)
                        .isEmpty();

                if (!isAvailable) {
                    throw new RuntimeException("Classroom is not available at the selected time");
                }
            }

            ClassSession firstSession = sessionsToReschedule.get(0);

            RescheduleRequest rescheduleRequest = new RescheduleRequest(
                    sessionsToReschedule,
                    calculateNewDateTime(firstSession.getDateTime(), newDateTimeTemplate),
                    newClassroom,
                    requestDto.newDuration(),
                    RequestStatus.PENDING,
                    lecturer,
                    firstSession.getCourse().getStudentRep(),
                    requestDto.requesterId(),
                    true
            );

            rescheduleRequestRepository.save(rescheduleRequest);

        } else {
            // Pojedyncza sesja
            ClassSession session = classSessionRepository.findById(requestDto.classSessionDto().id())
                    .orElseThrow(() -> new ClassSessionNotFoundException(requestDto.classSessionDto().id()));

            if (requestDto.classSessionDto().lecturer().id() != lecturer.getId()) {
                throw new RuntimeException("Unauthorized: not the session's teacher");
            }

            List<ClassSession> sessionsAtSameTime = classSessionRepository.findSessionsByClassroomAndTime(
                    newClassroom.getId(),
                    requestDto.newDateTime()
            );

            if (!sessionsAtSameTime.isEmpty()) {
                throw new RuntimeException("Classroom is not available at the selected time");
            }

            RescheduleRequest request = new RescheduleRequest(
                    List.of(session),
                    requestDto.newDateTime(),
                    newClassroom,
                    requestDto.newDuration(),
                    RequestStatus.PENDING,
                    lecturer,
                    session.getCourse().getStudentRep(),
                    requestDto.requesterId(),
                    false
            );

            rescheduleRequestRepository.save(request);
        }
    }

    public List<RescheduleRequestDto> getCompletedUserRequests(long userId) {
        List<RescheduleRequest> allCompletedRequests = rescheduleRequestRepository.findAll().stream()
                .filter(request -> request.getRequesterId() == userId)
                .filter(request -> request.getStatus() == RequestStatus.ACCEPTED || request.getStatus() == RequestStatus.REJECTED)
                .toList();

        //Grupowanie compeledRequests po ID spotkania
        Map<Long, List<RescheduleRequest>> requestsGroupedBySession = allCompletedRequests.stream()
                .flatMap(request -> request.getClassSessions().stream()
                        .map(session -> Map.entry(session.getId(), request)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        List<RescheduleRequestDto> result = new ArrayList<>();

        for (List<RescheduleRequest> sessionRequests : requestsGroupedBySession.values()) {
            Optional<RescheduleRequest> accepted = sessionRequests.stream()
                    .filter(request -> request.getStatus() == RequestStatus.ACCEPTED).findFirst();
            accepted.ifPresent(rescheduleRequest -> result.add(convertToDto(rescheduleRequest)));
        }

        return result;
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


        List<ClassSession> sessions = new ArrayList<>(request.getClassSessions());

        for (ClassSession session : sessions) {
            request.setOldTime(session.getDateTime());
            request.setOldDuration(session.getDuration());
            request.setOldClassroom(session.getClassroom());

            LocalDateTime newDateTime;
            //Jeżeli id > 0 to multiple request i przesuwamy date z requesta
            if (session.getId() > 0) {
                newDateTime = calculateNewDateTime(session.getDateTime(), request.getNewDateTime());

                boolean isAvailable = classSessionRepository
                        .findSessionsByClassroomAndTime(request.getNewClassroom().getId(), newDateTime)
                        .isEmpty();

                if (!isAvailable) {
                    throw new RuntimeException("Classroom is not available at the selected time");
                }

            } else {
                newDateTime = request.getNewDateTime();
            }

            session.setDateTime(newDateTime);
            session.setClassroom(request.getNewClassroom());
            session.setDuration(request.getNewDuration());
            classSessionRepository.save(session);

            // Odrzucanie innych requestów dla każdej sesji
            List<RescheduleRequest> otherRequests = new ArrayList<>(
                    rescheduleRequestRepository.findByClassSessionIdAndStatus(session.getId(), RequestStatus.PENDING)
            );

            for (RescheduleRequest otherRequest : otherRequests) {
                otherRequest.setStatus(RequestStatus.REJECTED);
            }
            rescheduleRequestRepository.saveAll(otherRequests);
        }

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
        ClassSessionDto firstSessionDto = null;
        if (!request.getClassSessions().isEmpty()) {
            firstSessionDto = classSessionService.convertToDto(request.getClassSessions().get(0));
        }

        return new RescheduleRequestDto(
                request.getId(),
                firstSessionDto,
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
