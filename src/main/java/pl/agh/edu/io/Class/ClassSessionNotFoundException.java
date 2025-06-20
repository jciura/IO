package pl.agh.edu.io.Class;

public class ClassSessionNotFoundException extends RuntimeException {
    public ClassSessionNotFoundException(long id) {
        super("Spotkanie o id: " + id + " nie znalezione.");
    }
}

