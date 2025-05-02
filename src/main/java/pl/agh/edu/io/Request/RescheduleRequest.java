package pl.agh.edu.io.Request;

import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Course.Course;
import pl.agh.edu.io.User.User;

import java.time.LocalDateTime;

@Entity
public class RescheduleRequest {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private ClassSession classSession;

    @ManyToOne
    private Course course;

    private LocalDateTime newDateTime;

    @ManyToOne
    private Classroom newClassroom;

    private RequestStatus status;

    @OneToOne
    private User lecturer;

    @OneToOne
    private User classRep;

    private boolean isForAllSessions;

    public RescheduleRequest() {
    }

    public RescheduleRequest(ClassSession classSession, LocalDateTime newDateTime, Classroom newClassroom
            , RequestStatus status, User lecturer, User classRep, boolean isForAllSessions) {
        this.classSession = classSession;
        this.newDateTime = newDateTime;
        this.newClassroom = newClassroom;
        this.status = status;
        this.lecturer = lecturer;
        this.classRep = classRep;
        this.isForAllSessions = isForAllSessions;
    }

    public Long getId() {
        return id;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public void setClassSession(ClassSession classSession) {
        this.classSession = classSession;
    }

    public Classroom getNewClassroom() {
        return newClassroom;
    }

    public void setNewClassroom(Classroom newClassroom) {
        this.newClassroom = newClassroom;
    }

    public LocalDateTime getNewDateTime() {
        return newDateTime;
    }

    public void setNewDateTime(LocalDateTime newDateTime) {
        this.newDateTime = newDateTime;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public User getClassRep() {
        return classRep;
    }

    public void setClassRep(User classRep) {
        this.classRep = classRep;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isForAllSessions() {
        return isForAllSessions;
    }

    public void setForAllSessions(boolean forAllSessions) {
        isForAllSessions = forAllSessions;
    }
}
