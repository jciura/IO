package pl.agh.edu.io.Class;

import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.User.UserDto;

import java.time.Duration;
import java.time.LocalDateTime;

public record ClassSessionDto(int id, String name, UserDto lecturer, UserDto classRep, ClassroomDto classroomDto,
                              LocalDateTime dateTime, Duration duration) {
}
