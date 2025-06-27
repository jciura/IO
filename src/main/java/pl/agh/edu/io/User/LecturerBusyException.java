package pl.agh.edu.io.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LecturerBusyException extends RuntimeException {
    public LecturerBusyException(String message) {
        super(message);
    }
}
