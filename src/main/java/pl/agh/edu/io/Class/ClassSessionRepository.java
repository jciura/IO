package pl.agh.edu.io.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.agh.edu.io.Course.Course;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
    @Query(value = "SELECT cs.* FROM classes cs WHERE cs.classroom_id = :classroomId " +
            "AND cs.date_time < :endDateTime " +
            "AND cs.date_time + (cs.duration * INTERVAL '1 minute') > :startDateTime",
            nativeQuery = true)
    List<ClassSession> findOverlappingSessions(
            @Param("classroomId") Long classroomId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    List<ClassSession> findByCourse(Course course);

    List<ClassSession> findAllByCourseId(long id);

    @Query("SELECT cs FROM ClassSession cs WHERE cs.course.lecturer.id = :lecturerId AND cs.dateTime <= :end AND cs.dateTime >= :start")
    List<ClassSession> findAllOverlappingByLecturer(
            @Param("lecturerId") long lecturerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
