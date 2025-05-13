package pl.agh.edu.io.recommendation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.agh.edu.io.Classroom.*;
import pl.agh.edu.io.Software.Software;
import pl.agh.edu.io.Software.SoftwareRepository;


@Service
public class RecommendationService {
    private final ClassroomService classroomService;
    private final ClassroomRepository classroomRepository;

    public RecommendationService(ClassroomService classroomService, ClassroomRepository classroomRepository) {
        this.classroomService = classroomService;
        this.classroomRepository = classroomRepository;
    }

    public List<Recommendation> getRecommendations(RecommendationRequest req) {
    	
        List<Classroom> all = classroomRepository.findAll();
        
        // TODO: filter only those free at 
        // req.getDateTime();
        // for
        // req.getDuration();
        // and sort by how many 'soft requirements' are fulfilled
        
        ClassroomDto oldClassroom = classroomService.getClassroomById(req.getClassroomId());

        return all.stream()
            .map(c -> {
                boolean isEnoughSpace = c.getCapacity() >= oldClassroom.capacity();
                boolean hasRequiredSoftware = false;
                boolean isEconomic = true;
                
                return new Recommendation(classroomService.convertToDto(c), isEnoughSpace, hasRequiredSoftware, isEconomic);
            })
            .collect(Collectors.toList());
    }
}
