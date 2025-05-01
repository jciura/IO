package pl.agh.edu.io.Class;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassSessionController {
    private final ClassSessionService classSessionService;

    public ClassSessionController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @GetMapping
    public ResponseEntity<List<ClassSessionDto>> getAllClasses() {
        return ResponseEntity.ok(classSessionService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSessionDto> getClassById(@PathVariable int id) {
        return ResponseEntity.ok(classSessionService.getClassById(id));
    }


}
