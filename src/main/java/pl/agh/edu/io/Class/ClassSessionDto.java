package pl.agh.edu.io.Class;

import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.User.UserDto;

import java.time.LocalDateTime;

public record ClassSessionDto(long id, String courseName, UserDto lecturer, UserDto classRep, ClassroomDto classroomDto,
                              LocalDateTime dateTime, long duration) {
}
