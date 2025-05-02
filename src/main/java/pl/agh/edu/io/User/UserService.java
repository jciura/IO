package pl.agh.edu.io.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.io.Course.CourseNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return convertToDto(user);
    }

    public User getUserEntityById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void delteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getCourses().isEmpty()) {
            throw new UserHasClassesException(id);
        }

        userRepository.delete(user);
    }

    public UserDto updateUser(long id, UserDto updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        user.setFirstName(updatedUser.firstName());
        user.setLastName(updatedUser.lastName());
        user.setEmail(updatedUser.email());
        user.setPhoneNumber(updatedUser.phoneNumber());
        user.setRole(updatedUser.role());

        return convertToDto(userRepository.save(user));
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}
