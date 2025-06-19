package pl.agh.edu.io.Course;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(long id) {
        super("Kurs o id: " + id + " nie znaleziony");
    }

    public CourseNotFoundException(String name) {
        super("Kurs " + name + " nie znaleziony");
    }
}
