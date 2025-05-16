package pl.agh.edu.io.Request;

import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Course.Course;
import pl.agh.edu.io.User.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class RescheduleRequest {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToMany
    private List<ClassSession> classSessions;

    @ManyToOne
    private Course course;

    private LocalDateTime newDateTime;

    @ManyToOne
    private Classroom newClassroom;

    private int newDuration;

    private RequestStatus status;

    @ManyToOne
    private User lecturer;

    @ManyToOne
    private User classRep;

    private int requesterId;

    private boolean isForAllSessions;

    @ManyToOne
    private Classroom oldClassroom;

    private LocalDateTime oldTime;

    private long oldDuration;

    private boolean confirmation;

    public RescheduleRequest() {
    }

    public RescheduleRequest(List<ClassSession> classSessions, LocalDateTime newDateTime, Classroom newClassroom,
                             int newDuration, RequestStatus status, User lecturer, User classRep, int requesterId,
                             boolean isForAllSessions) {
        this.classSessions = classSessions;
        this.newDateTime = newDateTime;
        this.newClassroom = newClassroom;
        this.newDuration = newDuration;
        this.status = status;
        this.lecturer = lecturer;
        this.classRep = classRep;
        this.requesterId = requesterId;
        this.isForAllSessions = isForAllSessions;
    }

    public Long getId() {
        return id;
    }

    public List<ClassSession> getClassSessions() {
        return classSessions;
    }

    public void setClassSessions(List<ClassSession> classSessions) {
        this.classSessions = classSessions;
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

    public int getNewDuration() {
        return newDuration;
    }

    public void setNewDuration(int newDuration) {
        this.newDuration = newDuration;
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

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
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

    public Classroom getOldClassroom() {
        return oldClassroom;
    }

    public void setOldClassroom(Classroom oldClassroom) {
        this.oldClassroom = oldClassroom;
    }

    public LocalDateTime getOldTime() {
        return oldTime;
    }

    public void setOldTime(LocalDateTime oldTime) {
        this.oldTime = oldTime;
    }

    public long getOldDuration() {
        return oldDuration;
    }

    public void setOldDuration(long oldDuration) {
        this.oldDuration = oldDuration;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}
