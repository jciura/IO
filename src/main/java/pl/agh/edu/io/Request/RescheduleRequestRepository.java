package pl.agh.edu.io.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RescheduleRequestRepository extends JpaRepository<RescheduleRequest, Long> {
    @Query("SELECT r FROM RescheduleRequest r JOIN r.classSessions cs " +
            "WHERE cs.id = :classSessionId AND r.status = :status")
    List<RescheduleRequest> findByClassSessionIdAndStatus(
            @Param("classSessionId") long classSessionId,
            @Param("status") RequestStatus status
    );
}
