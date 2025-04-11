package pl.agh.edu.io.Classroom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public Classroom getClassroomById(int id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public List<Classroom> getClassroomsBySoftware(String softwareName) {
        return classroomRepository.findAll().stream()
                .filter(classroom -> classroom.isHasComputers() &&
                        classroom.getSoftware().stream()
                                .anyMatch(software -> software.getName().equalsIgnoreCase(softwareName)))
                .collect(Collectors.toList());
    }
}
