package pl.agh.edu.io.recommendation;

import java.time.LocalDateTime;


public class RecommendationRequest {
	private long classroomId;
    private LocalDateTime dateTime;
	private long duration;
	
	
	public RecommendationRequest() { }

	public RecommendationRequest(long classroomId, LocalDateTime dateTime, long duration) {
		this.classroomId = classroomId;
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

	public long getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(long classroomId) {
		this.classroomId = classroomId;
	}
	
	
}
