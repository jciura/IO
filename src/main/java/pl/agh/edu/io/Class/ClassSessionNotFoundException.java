package pl.agh.edu.io.Class;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(int id) {
        super("Class with id: " + id + " not found");
    }
}

