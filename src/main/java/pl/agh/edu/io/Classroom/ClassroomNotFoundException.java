package pl.agh.edu.io.Classroom;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(String building, int number) {
        super("Classroom with number " + number + " not found in building " + building);
    }

    public ClassroomNotFoundException(long id) {
        super("Classroom with ID " + id + " not found.");
    }
}
