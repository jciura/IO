package pl.agh.edu.io.Request;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.agh.edu.io.Class.*;
import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Course.Course;
import pl.agh.edu.io.Course.CourseNotFoundException;
import pl.agh.edu.io.Course.CourseRepository;
import pl.agh.edu.io.SpecialDay.PolishDayOfWeek;
import pl.agh.edu.io.SpecialDay.SpecialDay;
import pl.agh.edu.io.SpecialDay.SpecialDayException;
import pl.agh.edu.io.SpecialDay.SpecialDayRepository;
import pl.agh.edu.io.User.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
    private final SpecialDayRepository specialDayRepository;

    public RescheduleRequestService(RescheduleRequestRepository rescheduleRequestRepository, ClassSessionRepository classSessionRepository, ClassroomRepository classroomRepository, UserService userService, ClassroomService classroomService,
                                    ClassSessionService classSessionService, CourseRepository courseRepository, SpecialDayRepository specialDayRepository) {
        this.rescheduleRequestRepository = rescheduleRequestRepository;
        this.classSessionRepository = classSessionRepository;
        this.classroomRepository = classroomRepository;
        this.userService = userService;
        this.classroomService = classroomService;
        this.classSessionService = classSessionService;
        this.courseRepository = courseRepository;
        this.specialDayRepository = specialDayRepository;
    }

    private boolean isMovingForward(RescheduleRequestDto requestDto) {
        LocalDate oldDate = requestDto.oldTime().toLocalDate();
        LocalDate newDate = requestDto.newDateTime().toLocalDate();
        return !newDate.isBefore(oldDate);
    }

    private LocalDateTime calculateNewDateTime(LocalDateTime currentDateTime, LocalDateTime newDateTime, boolean forward) {
        int newDayOfWeek = newDateTime.getDayOfWeek().getValue();
        LocalTime newTime = newDateTime.toLocalTime();

        int currentDayOfWeek = currentDateTime.getDayOfWeek().getValue();

        int dayDifference = newDayOfWeek - currentDayOfWeek;

        if (dayDifference == 0) {
            return currentDateTime.withHour(newTime.getHour())
                    .withMinute(newTime.getMinute())
                    .withSecond(0)
                    .withNano(0);
        }

        if (forward) {
            if (dayDifference < 0) {
                dayDifference += 7;
            }
            return currentDateTime.plusDays(dayDifference)
                    .withHour(newTime.getHour())
                    .withMinute(newTime.getMinute())
                    .withSecond(0)
                    .withNano(0);
        } else {
            if (dayDifference > 0) {
                dayDifference -= 7;
            }
            return currentDateTime.plusDays(dayDifference)
                    .withHour(newTime.getHour())
                    .withMinute(newTime.getMinute())
                    .withSecond(0)
                    .withNano(0);
        }
    }

    private boolean isLecturerBusy(User lecturer, LocalDateTime proposedStart, int proposedDurationMinutes) {
        LocalDateTime proposedEnd = proposedStart.plusMinutes(proposedDurationMinutes);

        List<ClassSessionDto> lecturerClasses = classSessionService.getAllClasses().stream()
                .filter(classSessionDto -> classSessionDto.lecturer().id() == lecturer.getId())
                .toList();


        return lecturerClasses.stream().anyMatch(session -> {
            LocalDateTime sessionStart = session.dateTime();
            LocalDateTime sessionEnd = sessionStart.plusMinutes(session.duration());
            return proposedStart.isBefore(sessionEnd) && proposedEnd.isAfter(sessionStart);
        });
    }

    //Wszystkie zmiany terminów działają w jednej funkcji
    @Transactional
    public void createRequest(RescheduleRequestDto requestDto, long userId) {
        User lecturer = userService.getUserEntityById(userId);

        if (lecturer.getRole() != UserRole.PROWADZACY) {
            throw new UnauthorizedException("Ten użytkownik nie jest prowadzącym");
        }

        ClassroomDto classroomDto = requestDto.newClassroom();
        Classroom newClassroom = classroomRepository
                .findByBuildingAndNumber(classroomDto.building(), classroomDto.number())
                .orElseThrow(() -> new ClassroomNotFoundException(classroomDto.building(), classroomDto.number()));


        if (requestDto.isForAllSessions()) {
            //Multiple
            Course course = courseRepository.findByName(requestDto.classSessionDto().courseName())
                    .orElseThrow(() -> new CourseNotFoundException(requestDto.classSessionDto().courseName()));

            List<ClassSession> allLecturerSessions = classSessionRepository.findAllByCourseId(course.getId());

            PolishDayOfWeek regularDay = course.getRegularDayOfWeek();

            List<ClassSession> sessionsToReschedule = allLecturerSessions.stream()
                    .filter(session -> session.getDateTime().isAfter(LocalDateTime.now()))
                    .filter(session -> PolishDayOfWeek.valueOf(session.getDateTime().getDayOfWeek().name()).equals(regularDay))
                    .sorted(Comparator.comparing(ClassSession::getDateTime))
                    .toList();

            LocalDateTime newDateTimeTemplate = requestDto.newDateTime();

            boolean forward = isMovingForward(requestDto);

            for (ClassSession session : sessionsToReschedule) {
                LocalDateTime newDateTime = calculateNewDateTime(session.getDateTime(), newDateTimeTemplate, forward);

                if (isLecturerBusy(lecturer, newDateTime, requestDto.newDuration())) {
                    throw new LecturerBusyException("Prowadzący ma już zajęcia w terminie: " + newDateTime);
                }

                //Trzeba ten wyjątek obsłużyć na froncie, wyświelimy czy na pewno chce, jak tak to trzeba wysłać nowy request tylko pole confirmation musi mieć
                //wartość true, za pierwyszym razem wysyłać z false
                Optional<SpecialDay> specialDayOpt = specialDayRepository.findByDate(newDateTime.toLocalDate());

                if (specialDayOpt.isPresent() && !requestDto.confirmation()) {
                    SpecialDay specialDay = specialDayOpt.get();
                    throw new SpecialDayException("Data: " + newDateTime + " to dzień specjalny i jest traktowana jako: " + specialDay.getTreatedAsPolishName());
                }

                LocalDateTime newEndDateTime = newDateTime.plusMinutes(requestDto.newDuration());

                boolean isAvailable = classSessionRepository
                        .findOverlappingSessions(newClassroom.getId(), newDateTime, newEndDateTime)
                        .isEmpty();

                if (!isAvailable) {
                    throw new ClassroomUnavailableException("Sala jest zajęta w wybranym terminie");
                }
            }

            ClassSession firstSession = sessionsToReschedule.get(0);

            RescheduleRequest rescheduleRequest = new RescheduleRequest(
                    sessionsToReschedule,
                    requestDto.newDateTime(),
                    newClassroom,
                    requestDto.newDuration(),
                    RequestStatus.PENDING,
                    lecturer,
                    firstSession.getCourse().getStudentRep(),
                    requestDto.requesterId(),
                    true,
                    requestDto.oldTime()
            );

            rescheduleRequestRepository.save(rescheduleRequest);

        } else {
            // Pojedyncza sesja
            ClassSession session = classSessionRepository.findById(requestDto.classSessionDto().id())
                    .orElseThrow(() -> new ClassSessionNotFoundException(requestDto.classSessionDto().id()));

            if (requestDto.classSessionDto().lecturer().id() != lecturer.getId()) {
                throw new UnauthorizedException("Brak uprawnień: użytkownik nie jest prowadzącym tego zajęcia");
            }

            LocalDateTime newDateTime = requestDto.newDateTime();
            LocalDateTime newEndDateTime = newDateTime.plusMinutes(requestDto.newDuration());


            boolean isAvailable = classSessionRepository
                    .findOverlappingSessions(newClassroom.getId(), newDateTime, newEndDateTime)
                    .isEmpty();

            if (!isAvailable) {
                throw new ClassroomUnavailableException("Sala jest zajęta w wybranym terminie");
            }

            if (isLecturerBusy(lecturer, newDateTime, requestDto.newDuration())) {
                throw new LecturerBusyException("Prowadzący ma już zajęcia w terminie: " + newDateTime);
            }

            Optional<SpecialDay> specialDayOpt = specialDayRepository.findByDate(newDateTime.toLocalDate());
            if (specialDayOpt.isPresent() && !requestDto.confirmation()) {
                SpecialDay specialDay = specialDayOpt.get();
                throw new SpecialDayException("Data: " + newDateTime + " to dzień specjalny i jest traktowana jako: " + specialDay.getTreatedAsPolishName());
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
                    false,
                    requestDto.oldTime()
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
            throw new IllegalStateException("Wniosek został już przetworzony");

        List<ClassSession> sessions = new ArrayList<>(request.getClassSessions());
        boolean forward = isMovingForward(convertToDto(request));

        for (ClassSession session : sessions) {
            Course course = session.getCourse();
            PolishDayOfWeek regularDay = course.getRegularDayOfWeek();
            PolishDayOfWeek sessionDay = PolishDayOfWeek.valueOf(session.getDateTime().getDayOfWeek().name());
            if (!sessionDay.equals(regularDay)) {
                continue;
            }

            request.setOldTime(session.getDateTime());
            request.setOldDuration(session.getDuration());
            request.setOldClassroom(session.getClassroom());

            LocalDateTime newDateTime = sessions.size() > 1
                    ? calculateNewDateTime(session.getDateTime(), request.getNewDateTime(), forward)
                    : request.getNewDateTime();
            LocalDateTime newEndTime = newDateTime.plusMinutes(request.getNewDuration());

            Optional<SpecialDay> specialDayOpt = specialDayRepository.findByDate(newDateTime.toLocalDate());
            if (specialDayOpt.isPresent()) {
                classSessionRepository.delete(session);
                continue;
            }

            boolean isAvailable = classSessionRepository
                    .findOverlappingSessions(request.getNewClassroom().getId(), newDateTime, newEndTime)
                    .isEmpty();

            if (!isAvailable) {
                throw new ClassroomUnavailableException("Sala jest zajęta w wybranym terminie");
            }

            session.setDateTime(newDateTime);
            session.setClassroom(request.getNewClassroom());
            session.setDuration(request.getNewDuration());
            classSessionRepository.save(session);

            List<RescheduleRequest> otherRequests = rescheduleRequestRepository
                    .findByClassSessionIdAndStatus(session.getId(), RequestStatus.PENDING);
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
            throw new IllegalStateException("Wniosek został już przetworzony");

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
                request.isForAllSessions(),
                request.isConfirmation()
        );
    }
}
