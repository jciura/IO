package pl.agh.edu.io.Class;

import jakarta.persistence.*;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.User.User;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "Classes")
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "classRep_id")
    private User classRep;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private LocalDateTime dateTime;
    private Duration duration;

    public ClassSession(String name, User lecturer, User classRep, Classroom classroom, LocalDateTime dateTime, Duration duration) {
        this.name = name;
        this.lecturer = lecturer;
        this.classRep = classRep;
        this.classroom = classroom;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    public ClassSession() {
    }

    public String getName() {
        return name;
    }

    public User getLecturer() {
        return lecturer;
    }

    public User getClassRep() {
        return classRep;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }


}
