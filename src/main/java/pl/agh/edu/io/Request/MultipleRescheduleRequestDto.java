package pl.agh.edu.io.Request;

import pl.agh.edu.io.Course.CourseDto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record MultipleRescheduleRequestDto(long id, CourseDto courseDto, DayOfWeek newDayOfWeek
        , LocalTime newLocalTime) {
}
