package pl.agh.edu.io.Request;

public class RescheduleRequestNotFoundException extends RuntimeException {
    public RescheduleRequestNotFoundException(long id) {
        super("Prośba o przeniesienie zajęć o id: " + id + " nie znaleziona.");
    }
}
