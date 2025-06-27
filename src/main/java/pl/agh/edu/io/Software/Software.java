package pl.agh.edu.io.Software;

import jakarta.persistence.*;
import pl.agh.edu.io.Classroom.Classroom;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "software")
    private Set<Classroom> classrooms = new HashSet<>();

    public Software(String name) {
        this.name = name;
    }

    public Software() {
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void removeClassroom(Classroom classroom) {
        classrooms.remove(classroom);
    }

}
