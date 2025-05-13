package pl.agh.edu.io.recommendation;

import pl.agh.edu.io.Classroom.Classroom;


public class Recommendation {
    private Classroom classroom;
    private boolean isEnoughSpace;
	private boolean hasRequiredSoftware;
    private boolean isEconomic;
    
    
    public Recommendation() { }
    
    public Recommendation(Classroom classroom, boolean isEnoughSpace, boolean hasRequiredSoftware, boolean isEconomic) {
    	this.setClassroom(classroom);
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

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
}

