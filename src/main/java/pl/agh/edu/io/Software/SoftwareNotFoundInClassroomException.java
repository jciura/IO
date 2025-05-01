package pl.agh.edu.io.Software;

public class SoftwareNotFoundInClassroomException extends RuntimeException {
    public SoftwareNotFoundInClassroomException(String softwareName, String building, int number) {
        super("Software " + softwareName + " not found in building: " + building + ",class number: " + number);
    }
}
