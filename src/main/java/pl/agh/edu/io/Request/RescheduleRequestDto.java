package pl.agh.edu.io.Request;

import pl.agh.edu.io.Class.ClassSessionDto;
import pl.agh.edu.io.Classroom.ClassroomDto;

import java.time.LocalDateTime;

public record RescheduleRequestDto(Long id,
                                   ClassSessionDto classSessionDto,
                                   int requesterId,
                                   ClassroomDto newClassroom,
                                   LocalDateTime newDateTime,
                                   int newDuration,
                                   ClassroomDto oldClassroom,
                                   LocalDateTime oldTime,
                                   long oldDuration,
                                   RequestStatus status,
                                   boolean isForAllSessions) {
}
