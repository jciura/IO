package pl.agh.edu.io.recommendation;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Software.SoftwareRepository;


@Service
public class RecommendationService {
    private final ClassroomService classroomService;
    private final ClassroomRepository classroomRepository;
    private final SoftwareRepository softwareRepository;

    public RecommendationService(
    		ClassroomService classroomService,
    		ClassroomRepository classroomRepository,
    		SoftwareRepository softwareRepository
    ) {
        this.classroomService = classroomService;
        this.classroomRepository = classroomRepository;
        this.softwareRepository = softwareRepository;
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
            .map(c -> createRecommendation(c, reference))
            .sorted(Comparator.comparingInt(this::countMatches)
                              .reversed())
            .collect(Collectors.toList());

    }
    
    private Recommendation createRecommendation(Classroom classroom, ClassroomDto reference) {
            boolean isEnoughSpace = classroom.getCapacity() >= reference.capacity();
            boolean hasRequiredSoftware = classroom.getSoftware().containsAll(
            		reference.softwareNames().stream()
            		.map(softwareRepository::findByName)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toSet()));
            boolean isOkComputers = classroom.isHasComputers() && reference.hasComputers();
            boolean isEconomic = true;

            return new Recommendation(
                classroomService.convertToDto(classroom),
                isEnoughSpace,
                hasRequiredSoftware,
                isEconomic,
                isOkComputers
            );
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
