package pl.agh.edu.io.Course;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(long id) {
        super("Course with id " + id + " not found");
    }

    public CourseNotFoundException(String name) {
        super("Course " + name + " not found");
    }
}
