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

    List<ClassSession> findByCourse(Course course);

    List<ClassSession> findAllByCourseId(long id);

    @Query("SELECT cs FROM ClassSession cs WHERE cs.course.lecturer.id = :lecturerId AND cs.dateTime <= :end AND cs.dateTime >= :start")
    List<ClassSession> findAllOverlappingByLecturer(
            @Param("lecturerId") long lecturerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
