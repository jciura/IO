package pl.agh.edu.io.recommendation;

import java.time.LocalDateTime;

import pl.agh.edu.io.Classroom.ClassroomDto;


public class RecommendationRequest {
	private ClassroomDto classroomDto;
    private LocalDateTime dateTime;
	private long duration;
	
	
	public RecommendationRequest() { }

	public RecommendationRequest(ClassroomDto classroomDto, LocalDateTime dateTime, long duration) {
		this.classroomDto = classroomDto;
		this.dateTime = dateTime;
		this.duration = duration;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public ClassroomDto getClassroomDto() {
		return classroomDto;
	}

	public void setClassroomDto(ClassroomDto classroomDto) {
		this.classroomDto = classroomDto;
	}
	
	
}
