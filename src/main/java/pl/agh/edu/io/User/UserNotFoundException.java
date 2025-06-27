package pl.agh.edu.io.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("Użytkownik z id: " + id + " nie znaleziony");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
