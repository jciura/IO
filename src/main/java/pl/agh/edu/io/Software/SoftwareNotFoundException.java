package pl.agh.edu.io.Software;

public class SoftwareNotFoundException extends RuntimeException {
    public SoftwareNotFoundException(String name) {
        super("Software " + name + " not found");
    }
}
