package pl.agh.edu.io.Software;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

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

}
