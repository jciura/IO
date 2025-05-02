package pl.agh.edu.io.User;

public record UserDto(long id, String firstName, String lastName, String email, String phoneNumber, UserRole role) {
}
