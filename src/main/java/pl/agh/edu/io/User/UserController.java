package pl.agh.edu.io.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<UserRole> getUserRole(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id).role());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.delteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UserDto updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> userLogin(@RequestBody LoginData loginData) {
        List<UserDto> allUsers = userService.getAllUsers();
        for (UserDto userDto: allUsers) {
            if (userDto.email().equals(loginData.email())) {
                User user = userService.getUserEntityById(userDto.id());
//                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//                System.out.println(loginData.password() + " " + user.getPassword() + " " + encoder.encode(loginData.password()));
//                if (encoder.matches(loginData.password(), user.getPassword()))
//                    return ResponseEntity.ok(userDto);
//                return ResponseEntity.badRequest().build();

                return ResponseEntity.ok(userDto);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
