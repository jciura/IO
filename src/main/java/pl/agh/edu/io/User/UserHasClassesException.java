package pl.agh.edu.io.User;

public class UserHasClassesException extends RuntimeException {
    public UserHasClassesException(long id) {
        super("Użytkownik z id: " + id + " jest zapisany na kursu, najpierw go usuń z kursów");
    }
}
