package pl.agh.edu.io.User;

public record UserDto(int id, String firstName, String lastName, String email, String phoneNumber, UserRole role) {
}
