package pl.agh.edu.io.Classroom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClassroomUnavailableException extends RuntimeException {
    public ClassroomUnavailableException(String message) {
        super(message);
    }
}
