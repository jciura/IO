package pl.agh.edu.io.Course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/{name}/{lecturerId}/{studentRepId}")
    public ResponseEntity<CourseDto> addCourse(
            @PathVariable String name,
            @PathVariable Long lecturerId,
            @PathVariable Long studentRepId
    ) {
        CourseDto createdCourse = courseService.addCourse(name, lecturerId, studentRepId);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable long id, @RequestBody CourseDto updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseDto>> getCoursesByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(courseService.getCoursesByUserId(userId));
    }
}
