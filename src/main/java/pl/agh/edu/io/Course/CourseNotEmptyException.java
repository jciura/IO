package pl.agh.edu.io.Course;

public class CourseNotEmptyException extends RuntimeException {
    public CourseNotEmptyException(long id) {
        super("Course " + id + " has scheduled classes");
    }
}
