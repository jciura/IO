package pl.agh.edu.io.Class;

import jakarta.persistence.*;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Course.Course;

import java.time.LocalDateTime;

@Entity
@Table(name = "Classes")
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private LocalDateTime dateTime;

    //Długość trwania w minutach
    private long duration;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public ClassSession(String name, Course course, Classroom classroom, LocalDateTime dateTime, long duration) {
        this.course = course;
        this.classroom = classroom;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    public ClassSession() {
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }


}
