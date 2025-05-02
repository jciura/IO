package pl.agh.edu.io.Class;

import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.Course.CourseDto;

import java.time.LocalDateTime;

public record ClassSessionDto(long id, CourseDto course, ClassroomDto classroomDto,
                              LocalDateTime dateTime, long duration) {
}
