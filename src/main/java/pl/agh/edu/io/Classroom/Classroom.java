package pl.agh.edu.io.Classroom;


import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Software.Software;

import java.util.Set;

@Entity
@Table(name = "Classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String building;
    private int number;
    private int floor;
    private int capacity;
    private boolean hasComputers;

    @OneToMany
    private Set<Software> software;

    @OneToMany(mappedBy = "classroom")
    private Set<ClassSession> classes;

    public Classroom() {
    }

    public Classroom(String building, int number, int floor, int capacity, boolean hasComputers, Set<Software> software) {
        this.building = building;
        this.number = number;
        this.floor = floor;
        this.capacity = capacity;
        this.hasComputers = hasComputers;
        this.software = software;
    }

    public String getBuilding() {
        return building;
    }

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isHasComputers() {
        return hasComputers;
    }

    public Set<Software> getSoftware() {
        return software;
    }

    public void addClass(ClassSession session) {
        this.classes.add(session);
    }
}
