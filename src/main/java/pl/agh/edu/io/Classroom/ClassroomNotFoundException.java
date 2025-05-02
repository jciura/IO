package pl.agh.edu.io.Classroom;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(long id) {
        super("Classroom with id: " + id + " not found");
    }
}
