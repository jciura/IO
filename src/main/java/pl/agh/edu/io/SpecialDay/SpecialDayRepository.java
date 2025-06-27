package pl.agh.edu.io.SpecialDay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SpecialDayRepository extends JpaRepository<SpecialDay, Integer> {
    Optional<SpecialDay> findByDate(LocalDate date);
}