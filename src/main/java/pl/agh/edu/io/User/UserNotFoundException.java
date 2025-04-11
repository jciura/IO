package pl.agh.edu.io.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User with id: " + id + " not found");
    }
}
