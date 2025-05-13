package pl.agh.edu.io.recommendation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Software.Software;


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
        
        ClassroomDto oldClassroom = req.getClassroom();

        return all.stream()
            .map(c -> {
                boolean isEnoughSpace = c.getCapacity() >= oldClassroom.capacity();
                boolean hasRequiredSoftware = false;
                boolean isEconomic = true;
                
                return new Recommendation(convertToDto(c), isEnoughSpace, hasRequiredSoftware, isEconomic);
            })
            .collect(Collectors.toList());
    }
    
    private ClassroomDto convertToDto(Classroom classroom) {
        List<String> softwareNames = classroom.getSoftware() != null
                ? classroom.getSoftware().stream()
                .map(Software::getName)
                .toList()
                : List.of();

        return new ClassroomDto(
                classroom.getId(),
                classroom.getBuilding(),
                classroom.getNumber(),
                classroom.getFloor(),
                classroom.getCapacity(),
                classroom.isHasComputers(),
                softwareNames
        );
    }
}
