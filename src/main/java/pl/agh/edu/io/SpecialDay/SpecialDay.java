package pl.agh.edu.io.SpecialDay;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
public class SpecialDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDate date;
    private DayOfWeek treatedAs;

    public SpecialDay(LocalDate date, DayOfWeek treatedAs) {
        this.date = date;
        this.treatedAs = treatedAs;
    }

    public SpecialDay() {
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getTreatedAs() {
        return treatedAs;
    }

    public String getTreatedAsPolishName() {
        return treatedAs == null ? null : PolishDayOfWeek.fromDayOfWeek(treatedAs);
    }

    public boolean isOverride() {
        return treatedAs != null;
    }
}

