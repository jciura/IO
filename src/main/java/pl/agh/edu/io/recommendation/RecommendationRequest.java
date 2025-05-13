package pl.agh.edu.io.recommendation;

import java.time.LocalDateTime;

import pl.agh.edu.io.Classroom.ClassroomDto;


public class RecommendationRequest {
	private ClassroomDto classroom;
    private LocalDateTime dateTime;
	private long duration;
	
	
	public RecommendationRequest() { }

	public RecommendationRequest(ClassroomDto classroom, LocalDateTime dateTime, long duration) {
		super();
		this.classroom = classroom;
		this.dateTime = dateTime;
		this.duration = duration;
	}

	ClassroomDto getClassroom() {
		return classroom;
	}

	public void setClassroom(ClassroomDto classroom) {
		this.classroom = classroom;
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
	
	
}
