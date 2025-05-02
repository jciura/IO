package pl.agh.edu.io.Classroom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.io.Software.Software;
import pl.agh.edu.io.Software.SoftwareNotFoundException;
import pl.agh.edu.io.Software.SoftwareNotFoundInClassroomException;
import pl.agh.edu.io.Software.SoftwareRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final SoftwareRepository softwareRepository;

    public ClassroomService(ClassroomRepository classroomRepository, SoftwareRepository softwareRepository) {
        this.classroomRepository = classroomRepository;
        this.softwareRepository = softwareRepository;
    }

    public ClassroomDto getClassroomById(long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));

        return convertToDto(classroom);
    }

    public List<ClassroomDto> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ClassroomDto> getClassroomsBySoftware(String softwareName) {
        List<Classroom> classrooms = classroomRepository.findAll().stream()
                .filter(classroom -> classroom.isHasComputers() &&
                        classroom.getSoftware().stream()
                                .anyMatch(software -> software.getName().equalsIgnoreCase(softwareName)))
                .toList();

        return classrooms.stream().map(this::convertToDto).toList();
    }

    public ClassroomDto createClassroom(ClassroomDto classroomDto) {
        Optional<Classroom> existingClassroom = classroomRepository.findByBuildingAndNumber(classroomDto.building(), classroomDto.number());

        if (existingClassroom.isPresent()) {
            throw new ClassroomAlreadyExistsException(classroomDto.building(), classroomDto.number());
        }

        Classroom classroom = new Classroom(
                classroomDto.building(),
                classroomDto.number(),
                classroomDto.floor(),
                classroomDto.capacity(),
                classroomDto.hasComputers()
        );

        return convertToDto(classroomRepository.save(classroom));
    }

    public void deleteClassroom(long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));

        if (!classroom.getClassSessions().isEmpty()) {
            throw new ClassroomInUseException(id);
        }

        classroomRepository.delete(classroom);
    }

    public ClassroomDto updateClassroom(long id, ClassroomDto classroomDto) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));

        classroom.setBuilding(classroomDto.building());
        classroom.setNumber(classroomDto.number());
        classroom.setFloor(classroomDto.floor());
        classroom.setCapacity(classroomDto.capacity());
        classroom.setHasComputers(classroomDto.hasComputers());

        return convertToDto(classroomRepository.save(classroom));
    }

    public void addSoftwareToClassroom(long classroomId, String softwareName) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ClassroomNotFoundException(classroomId));

        Software software = softwareRepository.findByName(softwareName)
                .orElseThrow(() -> new SoftwareNotFoundException(softwareName));

        classroom.addSoftware(software);
        software.addClassroom(classroom);
        classroomRepository.save(classroom);
        softwareRepository.save(software);
    }

    public void removeSoftwareFromClassroom(long classroomId, String softwareName) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ClassroomNotFoundException(classroomId));

        Set<Software> softwareSet = classroom.getSoftware();

        boolean removed = softwareSet.removeIf(s -> s.getName().equalsIgnoreCase(softwareName));

        if (!removed) {
            throw new SoftwareNotFoundInClassroomException(softwareName, classroom.getBuilding(), classroom.getNumber());
        }

        Software software = softwareRepository.findByName(softwareName).orElseThrow(() -> new SoftwareNotFoundException(softwareName));
        software.removeClassroom(classroom);

        softwareRepository.save(software);
        classroomRepository.save(classroom);
    }

    public List<ClassroomDto> getClassroomsByAttributes(
            String building,
            Integer number,
            Integer floor,
            Integer capacity,
            Boolean hasComputers,
            String softwareName) {

        List<Classroom> classrooms = classroomRepository.findAll();

        if (building != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.getBuilding().equals(building))
                    .collect(Collectors.toList());
        }
        if (number != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.getNumber() == number)
                    .collect(Collectors.toList());
        }
        if (floor != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.getFloor() == floor)
                    .collect(Collectors.toList());
        }
        if (capacity != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.getCapacity() >= capacity)
                    .collect(Collectors.toList());
        }
        if (hasComputers != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.isHasComputers() == hasComputers)
                    .collect(Collectors.toList());
        }
        if (softwareName != null) {
            classrooms = classrooms.stream()
                    .filter(classroom -> classroom.getSoftware().stream()
                            .anyMatch(software -> software.getName().equalsIgnoreCase(softwareName)))
                    .collect(Collectors.toList());
        }

        return classrooms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ClassroomDto convertToDto(Classroom classroom) {
        List<String> softwareNames = classroom.getSoftware() != null
                ? classroom.getSoftware().stream()
                .map(Software::getName)
                .toList()
                : List.of();

        return new ClassroomDto(
                classroom.getId(),
                classroom.getBuilding(),
                classroom.getNumber(),
                classroom.getFloor(),
                classroom.getCapacity(),
                classroom.isHasComputers(),
                softwareNames
        );
    }
}
