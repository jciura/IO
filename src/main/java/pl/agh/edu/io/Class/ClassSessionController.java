package pl.agh.edu.io.Class;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassById(@PathVariable int id) {
        classSessionService.deleteClassById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSessionDto> updateClass(@PathVariable int id, @RequestBody ClassSessionDto updatedClass) {
        ClassSessionDto updated = classSessionService.updateClass(id, updatedClass);
        return ResponseEntity.ok(updated);
    }

}
