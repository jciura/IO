package pl.agh.edu.io.Request;

public class RescheduleRequestNotFoundException extends RuntimeException {
    public RescheduleRequestNotFoundException(long id) {
        super("Reschedule request not found with id " + id);
    }
}
