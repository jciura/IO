package pl.agh.edu.io.recommendation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.Software.Software;


@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping("/getRecommendation")
    public ResponseEntity<List<Recommendation>> getRecommendation(
            @RequestBody RecommendationRequest req) {
        List<Recommendation> recs = service.getRecommendations(req);
        return ResponseEntity.ok(recs);
    }
}
