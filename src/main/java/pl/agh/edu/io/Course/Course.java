package pl.agh.edu.io.Course;

import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.User.User;

import java.util.ArrayList;
import java.util.List;


//Zawiera wiele ClassSession, zrobiona żeby łatwo przełożyć wszystkie zajęcia w kursie
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Prowadzacy zajecia
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    // Starosta reprezentujący grupę przypisaną do tego kursu
    @ManyToOne
    @JoinColumn(name = "representative_id")
    private User studentRep;

    // Wszystkie zajęcia w ramach kursu
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSession> sessions = new ArrayList<>();

    public Course() {
    }

    public Course(String name, User lecturer, User studentRep) {
        this.name = name;
        this.lecturer = lecturer;
        this.studentRep = studentRep;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public User getStudentRep() {
        return studentRep;
    }

    public void setStudentRep(User studentRep) {
        this.studentRep = studentRep;
    }

    public List<ClassSession> getSessions() {
        return sessions;
    }

    public void addSession(ClassSession session) {
        sessions.add(session);
        session.setCourse(this);
    }

    public void removeSession(ClassSession session) {
        sessions.remove(session);
        session.setCourse(null);
    }
}
