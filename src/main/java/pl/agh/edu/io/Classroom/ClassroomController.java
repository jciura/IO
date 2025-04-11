package pl.agh.edu.io.Classroom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable int id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/software/{softwareName}")
    public ResponseEntity<List<Classroom>> getClassroomBySoftware(@PathVariable String softwareName) {
        return ResponseEntity.ok(classroomService.getClassroomsBySoftware(softwareName));
    }


}
