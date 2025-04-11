package pl.agh.edu.io.Class;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassSessionService {
    private final ClassSessionRepository classSessionRepository;


    public ClassSessionService(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public ClassSession getClassById(int id) {
        return classSessionRepository.findById(id)
                .orElseThrow(() -> new ClassSessionNotFoundException(id));
    }

    public List<ClassSession> getAllClasses() {
        return classSessionRepository.findAll();
    }
}
