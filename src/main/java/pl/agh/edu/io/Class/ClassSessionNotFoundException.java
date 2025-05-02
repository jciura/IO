package pl.agh.edu.io.Class;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(long id) {
        super("Class with id: " + id + " not found");
    }
}

