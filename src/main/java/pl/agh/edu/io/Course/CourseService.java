package pl.agh.edu.io.Course;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.agh.edu.io.Class.ClassSessionService;
import pl.agh.edu.io.User.User;
import pl.agh.edu.io.User.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final ClassSessionService classSessionService;

    public CourseService(CourseRepository courseRepository, UserService userService, @Lazy ClassSessionService classSessionService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.classSessionService = classSessionService;
    }

    public CourseDto getCourseById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        return convertToDto(course);
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteCourseById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        if (!course.getSessions().isEmpty()) {
            throw new CourseNotEmptyException(id);
        }

        courseRepository.delete(course);
    }

    public CourseDto updateCourse(long id, CourseDto updatedCourse) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        course.setName(updatedCourse.name());

        User newLecturer = userService.getUserEntityById(updatedCourse.lecturer().id());
        User newStudentRep = userService.getUserEntityById(updatedCourse.studentRep().id());


        course.setLecturer(newLecturer);
        course.setStudentRep(newStudentRep);
        return convertToDto(courseRepository.save(course));
    }

    public List<CourseDto> getCoursesByUserId(long userId) {
        return courseRepository.findAll().stream()
                .filter(course ->
                        course.getLecturer().getId() == userId ||
                                (course.getStudentRep() != null && course.getStudentRep().getId() == userId)
                )
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CourseDto convertToDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                userService.convertToDto(course.getLecturer()),
                userService.convertToDto(course.getStudentRep()),
                course.getSessions().stream()
                        .map(classSessionService::convertToDto)
                        .collect(Collectors.toList())
        );
    }
}
