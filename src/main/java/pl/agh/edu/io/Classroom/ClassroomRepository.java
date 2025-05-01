package pl.agh.edu.io.Classroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Optional<Classroom> findByBuildingAndNumber(String building, int number);
}
