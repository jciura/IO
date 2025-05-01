package pl.agh.edu.io.Classroom;


import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Software.Software;

import java.util.HashSet;
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

    @ManyToMany
    @JoinTable(
            name = "classroom_software",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id")
    )
    private Set<Software> software = new HashSet<>();

    @OneToMany(mappedBy = "classroom")
    private Set<ClassSession> classes = new HashSet<ClassSession>();

    public Classroom() {
    }

    public Classroom(String building, int number, int floor, int capacity, boolean hasComputers) {
        this.building = building;
        this.number = number;
        this.floor = floor;
        this.capacity = capacity;
        this.hasComputers = hasComputers;
    }

    public Classroom(String building, int number, int floor, int capacity, boolean hasComputers, Set<Software> software) {
        this.building = building;
        this.number = number;
        this.floor = floor;
        this.capacity = capacity;
        this.hasComputers = hasComputers;
        this.software = software;
    }

    public int getId() {
        return id;
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

    public Set<ClassSession> getClassSessions() {
        return classes;
    }

    public boolean isHasComputers() {
        return hasComputers;
    }

    public Set<Software> getSoftware() {
        return software;
    }

    public void addSoftware(Software software) {
        this.software.add(software);
    }

    public void addClass(ClassSession session) {
        this.classes.add(session);
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setHasComputers(boolean hasComputers) {
        this.hasComputers = hasComputers;
    }
}
