package pl.agh.edu.io.Classroom;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(String building, int number) {
        super("Klasa o numerze: " + number + " nie znaleziona w budynku " + building);
    }

    public ClassroomNotFoundException(long id) {
        super("Klasa o id: " + id + " nie znaleziony.");
    }
}
