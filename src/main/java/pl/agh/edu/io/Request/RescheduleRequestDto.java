package pl.agh.edu.io.Request;

import pl.agh.edu.io.Class.ClassSessionDto;
import pl.agh.edu.io.Classroom.ClassroomDto;

import java.time.LocalDateTime;

public record RescheduleRequestDto(Long id,
                                   ClassSessionDto classSessionDto,
                                   ClassroomDto newClassroom,
                                   LocalDateTime newDateTime,
                                   int newDuration,
                                   RequestStatus status,
                                   boolean isForAllSessions) {
}
