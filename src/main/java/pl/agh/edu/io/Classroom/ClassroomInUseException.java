package pl.agh.edu.io.Classroom;

public class ClassroomInUseException extends RuntimeException {
    public ClassroomInUseException(long id) {
        super("Klasa o id: " + id + " ma wciąż zaplanowane spotkania.");
    }
}
