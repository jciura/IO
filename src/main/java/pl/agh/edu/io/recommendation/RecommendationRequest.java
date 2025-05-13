package pl.agh.edu.io.recommendation;

import java.time.LocalDateTime;
import pl.agh.edu.io.Classroom.Classroom;


public class RecommendationRequest {
	private Classroom classroom;
    private LocalDateTime dateTime;
	private long duration;
	
	public RecommendationRequest(Classroom classroom, LocalDateTime dateTime, long duration) {
		super();
		this.classroom = classroom;
		this.dateTime = dateTime;
		this.duration = duration;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
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
