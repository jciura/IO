package pl.agh.edu.io.Class;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.io.Course.CourseDto;
import pl.agh.edu.io.Course.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classes")
public class ClassSessionController {
    private final ClassSessionService classSessionService;
    private final CourseService courseService;

    public ClassSessionController(ClassSessionService classSessionService, CourseService courseService) {
        this.classSessionService = classSessionService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<ClassSessionDto>> getAllClasses() {
        return ResponseEntity.ok(classSessionService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSessionDto> getClassById(@PathVariable int id) {
        return ResponseEntity.ok(classSessionService.getClassById(id));
    }

    @GetMapping("/course_id/{courseId}")
    public ResponseEntity<List<ClassSessionDto>> getClassesWithinCourse(@PathVariable int courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        List<ClassSessionDto> classSessionDtos = classSessionService.getAllClasses().stream()
                .filter(classSessionDto -> classSessionDto.courseName().equals(courseDto.name()))
                .toList();
        return ResponseEntity.ok(classSessionDtos);
    }

    @GetMapping("/user_id/{userId}")
    public ResponseEntity<List<ClassSessionDto>> getClassesOfUser(@PathVariable int userId) {
        List<ClassSessionDto> classSessionDtos = classSessionService.getAllClasses().stream()
                .filter(classSessionDto -> classSessionDto.lecturer().id() == userId ||
                        classSessionDto.classRep().id() == userId)
                .toList();
        return ResponseEntity.ok(classSessionDtos);
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
