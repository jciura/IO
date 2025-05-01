package pl.agh.edu.io.Software;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoftwareRepository extends JpaRepository<Software, Integer> {
    Optional<Software> findByName(String softwareName);
}
