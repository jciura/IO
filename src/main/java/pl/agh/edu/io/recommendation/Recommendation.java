package pl.agh.edu.io.recommendation;

import pl.agh.edu.io.Classroom.ClassroomDto;


public class Recommendation {
    private ClassroomDto classroomDto;
    private boolean isEnoughSpace;
	private boolean isRequiredSoftware;
    private boolean isEconomic;
    private boolean isOkComputers;
    
    
    public Recommendation() { }
    
    public Recommendation(ClassroomDto classroomDto, boolean isEnoughSpace, boolean isRequiredSoftware, boolean isEconomic, boolean isOkComputers) {
    	this.classroomDto = classroomDto;
    	this.isEnoughSpace = isEnoughSpace;
    	this.isRequiredSoftware = isRequiredSoftware;
    	this.isEconomic = isEconomic;
    	this.isOkComputers = isOkComputers;
    }
    
    
    public boolean isEnoughSpace() {
		return isEnoughSpace;
	}

	public void setEnoughSpace(boolean isEnoughSpace) {
		this.isEnoughSpace = isEnoughSpace;
	}

	public boolean isRequiredSoftware() {
		return isRequiredSoftware;
	}

	public void setRequiredSoftware(boolean isRequiredSoftware) {
		this.isRequiredSoftware = isRequiredSoftware;
	}

	public boolean isEconomic() {
		return isEconomic;
	}

	public void setEconomic(boolean isEconomic) {
		this.isEconomic = isEconomic;
	}

	public ClassroomDto getClassroomDto() {
		return classroomDto;
	}

	public void setClassroomDto(ClassroomDto classroomDto) {
		this.classroomDto = classroomDto;
	}

	public boolean isOkComputers() {
		return isOkComputers;
	}

	public void setOkComputers(boolean isOkComputers) {
		this.isOkComputers = isOkComputers;
	}
}

