package pl.agh.edu.io.Class;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.io.Classroom.ClassroomDto;
import pl.agh.edu.io.Software.Software;
import pl.agh.edu.io.User.UserService;

import java.util.List;

@Service
@Transactional
public class ClassSessionService {
    private final ClassSessionRepository classSessionRepository;
    private final UserService userService;


    public ClassSessionService(ClassSessionRepository classSessionRepository, UserService userService) {
        this.classSessionRepository = classSessionRepository;
        this.userService = userService;
    }

    public ClassSessionDto getClassById(int id) {
        ClassSession classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new ClassSessionNotFoundException(id));

        return convertToDto(classSessionRepository.save(classSession));
    }

    public List<ClassSessionDto> getAllClasses() {
        return classSessionRepository.findAll().stream()
                .map(this::convertToDto).toList();
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
                classSession.getName(),
                userService.convertToDto(classSession.getLecturer()),
                userService.convertToDto(classSession.getClassRep()),
                classRoomDto,
                classSession.getDateTime(),
                classSession.getDuration()
        );
    }
}
