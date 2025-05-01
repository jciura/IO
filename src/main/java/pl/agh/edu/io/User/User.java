package pl.agh.edu.io.User;

import jakarta.persistence.*;
import pl.agh.edu.io.Class.ClassSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private String password;

    @OneToMany(mappedBy = "lecturer")
    private List<ClassSession> taughtClasses = new ArrayList<>();

    @OneToMany(mappedBy = "classRep")
    private List<ClassSession> representedClasses = new ArrayList<>();



    public User(String firstName, String lastName, String email, String phoneNumber, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ClassSession> getTaughtClasses() {
        if (role == UserRole.PROWADZACY) {
            return taughtClasses;
        }
        return Collections.emptyList();
    }

    public List<ClassSession> getRepresentedClasses() {
        if (role == UserRole.STAROSTA) {
            return representedClasses;
        }
        return Collections.emptyList();
    }
}
