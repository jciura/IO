package pl.agh.edu.io.Class;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.Classroom.ClassroomRepository;
import pl.agh.edu.io.Software.Software;
import pl.agh.edu.io.User.UserService;

import java.util.List;

@Service
@Transactional
public class ClassSessionService {
    private final ClassSessionRepository classSessionRepository;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;

    public ClassSessionService(ClassSessionRepository classSessionRepository, ClassroomRepository classroomRepository, UserService userService) {
        this.classSessionRepository = classSessionRepository;
        this.classroomRepository = classroomRepository;
        this.userService = userService;
    }

    public ClassSessionDto getClassById(long id) {
        ClassSession classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new ClassSessionNotFoundException(id));

        return convertToDto(classSessionRepository.save(classSession));
    }

    public List<ClassSessionDto> getAllClasses() {
        return classSessionRepository.findAll().stream()
                .map(this::convertToDto).toList();
    }

    public void deleteClassById(long id) {
        ClassSession classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new ClassSessionNotFoundException(id));

        classSessionRepository.delete(classSession);
    }

    public ClassSessionDto updateClass(long id, ClassSessionDto updatedDto) {
        ClassSession classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new ClassSessionNotFoundException(id));

        classSession.setDateTime(updatedDto.dateTime());
        classSession.setDuration(updatedDto.duration());

        classSession.setClassroom(
                classroomRepository.findById(updatedDto.classroomDto().id())
                        .orElseThrow(() -> new RuntimeException("Classroom not found"))
        );

        return convertToDto(classSessionRepository.save(classSession));
    }

    public ClassSessionDto convertToDto(ClassSession classSession) {
        List<String> softwareNames = classSession.getClassroom().getSoftware() != null
                ? classSession.getClassroom().getSoftware().stream()
                .map(Software::getName)
                .toList()
                : List.of();

        ClassroomDto classRoomDto = new ClassroomDto(
                classSession.getClassroom().getId(),
                classSession.getClassroom().getBuilding(),
                classSession.getClassroom().getNumber(),
                classSession.getClassroom().getFloor(),
                classSession.getClassroom().getCapacity(),
                classSession.getClassroom().isHasComputers(),
                softwareNames
        );

        return new ClassSessionDto(
                classSession.getId(),
                classSession.getCourse().getName(),
                userService.convertToDto(classSession.getCourse().getLecturer()),
                userService.convertToDto(classSession.getCourse().getStudentRep()),
                classRoomDto,
                classSession.getDateTime(),
                classSession.getDuration()
        );
    }
}
