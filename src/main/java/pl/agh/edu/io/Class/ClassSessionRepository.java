package pl.agh.edu.io.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.agh.edu.io.Course.Course;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
    @Query("SELECT c FROM ClassSession cs JOIN cs.classroom c WHERE cs.dateTime = :dateTime AND c.id = :classroomId")
    List<ClassSession> findSessionsByClassroomAndTime(@Param("classroomId") Long classroomId, @Param("dateTime") LocalDateTime dateTime);
    
    @Query("""
    	    SELECT cs
    	      FROM ClassSession cs
    	     WHERE cs.classroom.id = :classroomId
    	       AND cs.dateTime >= :fromDateTime
    	       AND cs.dateTime < :toDateTime
    		""")
	List<ClassSession> findSessionsByClassroomAndTimeRange(
	    @Param("classroomId") Long classroomId,
	    @Param("fromDateTime") LocalDateTime fromDateTime,
	    @Param("toDateTime") LocalDateTime toDateTime
	);

    List<ClassSession> findByCourse(Course course);
}
