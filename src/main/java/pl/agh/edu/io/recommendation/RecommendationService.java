package pl.agh.edu.io.recommendation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Class.ClassSessionRepository;
import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Software.SoftwareRepository;


@Service
public class RecommendationService {
    private final ClassroomService classroomService;
    private final ClassroomRepository classroomRepository;
    private final SoftwareRepository softwareRepository;
    private final ClassSessionRepository classSessionRepository;

    public RecommendationService(
    		ClassroomService classroomService,
    		ClassroomRepository classroomRepository,
    		SoftwareRepository softwareRepository,
    		ClassSessionRepository classSessionRepository
    ) {
        this.classroomService = classroomService;
        this.classroomRepository = classroomRepository;
        this.softwareRepository = softwareRepository;
        this.classSessionRepository = classSessionRepository;
    }

    public List<Recommendation> getRecommendations(RecommendationRequest req) {
    	LocalDateTime reqStart = req.getDateTime();
        LocalDateTime reqEnd = reqStart.plusMinutes(req.getDuration());

        // Fetch only free classrooms
        List<Classroom> free = classroomRepository.findAllAvailable(reqStart, reqEnd);

        // Fetch the reference classroom
        ClassroomDto reference = req.getClassroomDto();

        // Map to Recommendation and sort by how many 'soft requirements' filled
        return free.stream()
            .map(c -> createRecommendation(c, reference, reqStart, reqEnd))
            .sorted(Comparator.comparingInt(this::countMatches)
                              .reversed())
            .collect(Collectors.toList());

    }
    
    private Recommendation createRecommendation(
    		Classroom classroom,
    		ClassroomDto reference,
    		LocalDateTime reqStart,
    		LocalDateTime reqEnd
		) {
        boolean isEnoughSpace = classroom.getCapacity() >= reference.capacity();
        boolean hasRequiredSoftware = classroom.getSoftware().containsAll(
        		reference.softwareNames().stream()
        		.map(softwareRepository::findByName)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet()));
        boolean isOkComputers = classroom.isHasComputers() && reference.hasComputers();
        boolean isEconomic = isEconomic(classroom, reqStart, reqEnd);

        return new Recommendation(
            classroomService.convertToDto(classroom),
            isEnoughSpace,
            hasRequiredSoftware,
            isEconomic,
            isOkComputers
        );
    }
    
    private boolean isEconomic(
		Classroom classroom,
		LocalDateTime reqStart,
		LocalDateTime reqEnd
	) {
    	LocalDateTime dayStart = reqStart.toLocalDate().atStartOfDay();
    	LocalDateTime dayEnd = reqEnd.toLocalDate().plusDays(1).atStartOfDay();
    	
    	List<ClassSession> sessionsInDay = classSessionRepository
    			.findSessionsByClassroomAndTimeRange(classroom.getId(), dayStart, dayEnd);
    	
        if (sessionsInDay.isEmpty()) {
            // No sessions in the day - would need to clean an extra classroom
            return false;
        }

        // Find the last session for the day
        ClassSession lastSession = sessionsInDay.stream()
                .max(Comparator.comparing(ClassSession::getDateTime))
                .orElseThrow(); // safe because we already checked for empty

        LocalDateTime lastSessionEnd = lastSession.getDateTime()
                .plusMinutes(lastSession.getDuration());

        boolean afterLastSession = reqStart.isAfter(lastSessionEnd);
        boolean endsLate = reqEnd.getHour() > 16;
        boolean gapTooBig = Duration.between(lastSessionEnd, reqStart).toHours() >= 1;

        boolean isEconomic = !(afterLastSession && (endsLate || gapTooBig));
    	
    	return isEconomic;
    }

    private int countMatches(Recommendation rec) {
        int score = 0;
        if (rec.isEnoughSpace()) score++;
        if (rec.isRequiredSoftware()) score++;
        if (rec.isEconomic()) score++;
        if (rec.isOkComputers()) score++;
        return score;
    }
}
