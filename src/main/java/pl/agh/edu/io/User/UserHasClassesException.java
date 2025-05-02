package pl.agh.edu.io.User;

public class UserHasClassesException extends RuntimeException {
    public UserHasClassesException(long id) {
        super("User with id: " + id + " still has attached courses");
    }
}
