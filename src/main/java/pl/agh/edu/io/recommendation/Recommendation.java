package pl.agh.edu.io.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.agh.edu.io.Classroom.ClassroomDto;


public class Recommendation {
	@JsonIgnoreProperties("classes")
    private ClassroomDto classroom;
    private boolean isEnoughSpace;
	private boolean hasRequiredSoftware;
    private boolean isEconomic;
    
    
    public Recommendation() { }
    
    public Recommendation(ClassroomDto classroom, boolean isEnoughSpace, boolean hasRequiredSoftware, boolean isEconomic) {
    	this.classroom = classroom;
    	this.isEnoughSpace = isEnoughSpace;
    	this.hasRequiredSoftware = hasRequiredSoftware;
    	this.isEconomic = isEconomic;
    }
    
    
    public boolean isEnoughSpace() {
		return isEnoughSpace;
	}

	public void setEnoughSpace(boolean isEnoughSpace) {
		this.isEnoughSpace = isEnoughSpace;
	}

	public boolean isHasRequiredSoftware() {
		return hasRequiredSoftware;
	}

	public void setHasRequiredSoftware(boolean hasRequiredSoftware) {
		this.hasRequiredSoftware = hasRequiredSoftware;
	}

	public boolean isEconomic() {
		return isEconomic;
	}

	public void setEconomic(boolean isEconomic) {
		this.isEconomic = isEconomic;
	}

	public ClassroomDto getClassroom() {
		return classroom;
	}

	public void setClassroom(ClassroomDto classroom) {
		this.classroom = classroom;
	}
}

