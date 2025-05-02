package pl.agh.edu.io.Course;

import pl.agh.edu.io.Class.ClassSessionDto;
import pl.agh.edu.io.User.UserDto;

import java.util.List;

public record CourseDto(long id, String name, UserDto lecturer, UserDto studentRep, List<ClassSessionDto> sessions) {
}
