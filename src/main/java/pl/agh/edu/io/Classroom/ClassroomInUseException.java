package pl.agh.edu.io.Classroom;

public class ClassroomInUseException extends RuntimeException {
    public ClassroomInUseException(int id) {
        super("Clasroom with id: " + id + " has scheduled classes");
    }
}
