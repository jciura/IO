package pl.agh.edu.io.Course;

public class CourseNotEmptyException extends RuntimeException {
    public CourseNotEmptyException(long id) {
        super("Kurs o id: " + id + " ma wciąż zaplanowane spotkania, najpierw je usuń.");
    }
}
