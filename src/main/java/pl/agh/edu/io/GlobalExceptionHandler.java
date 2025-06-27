package pl.agh.edu.io;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.agh.edu.io.Class.ClassSessionNotFoundException;
import pl.agh.edu.io.Classroom.ClassroomAlreadyExistsException;
import pl.agh.edu.io.Classroom.ClassroomInUseException;
import pl.agh.edu.io.Classroom.ClassroomNotFoundException;
import pl.agh.edu.io.Classroom.ClassroomUnavailableException;
import pl.agh.edu.io.Course.CourseNotEmptyException;
import pl.agh.edu.io.Course.CourseNotFoundException;
import pl.agh.edu.io.Request.RescheduleRequestNotFoundException;
import pl.agh.edu.io.Software.SoftwareNotFoundException;
import pl.agh.edu.io.Software.SoftwareNotFoundInClassroomException;
import pl.agh.edu.io.User.LecturerBusyException;
import pl.agh.edu.io.User.UserHasClassesException;
import pl.agh.edu.io.User.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClassSessionNotFoundException.class)
    public ResponseEntity<String> handleClassSessionNotFound(ClassSessionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClassroomAlreadyExistsException.class)
    public ResponseEntity<String> handleClassroomAlreadyExists(ClassroomAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ClassroomInUseException.class)
    public ResponseEntity<String> handleClassroomInUse(ClassroomInUseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClassroomNotFoundException.class)
    public ResponseEntity<String> handleClassroomNotFound(ClassroomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClassroomUnavailableException.class)
    public ResponseEntity<String> handleClassroomUnavailable(ClassroomUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CourseNotEmptyException.class)
    public ResponseEntity<String> handleCourseNotEmpty(CourseNotEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFound(CourseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RescheduleRequestNotFoundException.class)
    public ResponseEntity<String> handleRescheduleRequestNotFound(RescheduleRequestNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SoftwareNotFoundException.class)
    public ResponseEntity<String> handleSoftwareNotFound(SoftwareNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SoftwareNotFoundInClassroomException.class)
    public ResponseEntity<String> handleSoftwareNotFoundInClassroom(SoftwareNotFoundInClassroomException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LecturerBusyException.class)
    public ResponseEntity<String> handleLecturerBusy(LecturerBusyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasClassesException.class)
    public ResponseEntity<String> handleUserHasClasses(UserHasClassesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
