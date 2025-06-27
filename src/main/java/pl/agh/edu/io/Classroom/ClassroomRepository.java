package pl.agh.edu.io.Classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByBuildingAndNumber(String building, int number);
    
    @Query("""
            SELECT c
              FROM Classroom c
             WHERE NOT EXISTS (
                 SELECT s
                   FROM ClassSession s
                  WHERE s.classroom    = c
                    AND s.dateTime     < :reqEnd
                    AND timestampadd(
                          MINUTE,
                          s.duration,
                          s.dateTime
                        ) > :reqStart
             )
            """)
    List<Classroom> findAllAvailable(@Param("reqStart") LocalDateTime reqStart, @Param("reqEnd") LocalDateTime reqEnd);
}

	
