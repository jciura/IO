package pl.agh.edu.io.Software;

public class SoftwareNotFoundInClassroomException extends RuntimeException {
    public SoftwareNotFoundInClassroomException(String softwareName, String building, int number) {
        super("Software " + softwareName + " nie znaleziony w budynku: " + building + ",numer klasy: " + number);
    }
}
