package pl.agh.edu.io.recommendation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.agh.edu.io.Classroom.*;


@Service
public class RecommendationService {

    private final ClassroomRepository classroomRepo;

    public RecommendationService(ClassroomRepository classroomRepo) {
        this.classroomRepo = classroomRepo;
    }

    public List<Recommendation> getRecommendations(RecommendationRequest req) {
        List<Classroom> all = classroomRepo.findAll();
        
        // TODO: filter only those free at 
        // req.getDateTime();
        // for
        // req.getDuration();
        // and sort by how many 'soft requirements' are fulfilled
        
        Classroom oldClassroom = req.getClassroom();

        return all.stream()
            .map(c -> {
                boolean isEnoughSpace = c.getCapacity() >= oldClassroom.getCapacity();
                boolean hasRequiredSoftware = c.getSoftware().containsAll(oldClassroom.getSoftware());
                boolean isEconomic = true;

                return new Recommendation(c, isEnoughSpace, hasRequiredSoftware, isEconomic);
            })
            .collect(Collectors.toList());
    }
}
