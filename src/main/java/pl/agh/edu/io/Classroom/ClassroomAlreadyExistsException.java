package pl.agh.edu.io.Classroom;

public class ClassroomAlreadyExistsException extends RuntimeException {
    public ClassroomAlreadyExistsException(String building, int number) {
        super("Classroom in building '" + building + " with number: " + number + " already exists");
    }
}
