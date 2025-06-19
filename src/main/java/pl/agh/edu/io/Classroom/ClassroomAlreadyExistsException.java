package pl.agh.edu.io.Classroom;

public class ClassroomAlreadyExistsException extends RuntimeException {
    public ClassroomAlreadyExistsException(String building, int number) {
        super("Klasa w budynku: " + building + " o numerze: " + number + " już istnieje");
    }
}
