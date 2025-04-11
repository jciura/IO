package pl.agh.edu.io;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Class.ClassSessionRepository;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Classroom.ClassroomRepository;
import pl.agh.edu.io.Software.Software;
import pl.agh.edu.io.Software.SoftwareRepository;
import pl.agh.edu.io.User.User;
import pl.agh.edu.io.User.UserRepository;
import pl.agh.edu.io.User.UserRole;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final ClassSessionRepository classSessionRepository;
    private final SoftwareRepository softwareRepository;

    public DataInitializer(UserRepository userRepository, ClassroomRepository classroomRepository, ClassSessionRepository classSessionRepository, SoftwareRepository softwareRepository) {
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.classSessionRepository = classSessionRepository;
        this.softwareRepository = softwareRepository;
    }

    @Override
    public void run(String... args) {
        User lecturer = new User("Adam", "Pompon", "adam@pompon.com", "999999999", UserRole.PROWADZACY);
        User classRep = new User("starosta", "starosta", "starosta@starosta.com", "111111111", UserRole.STAROSTA);
        userRepository.save(lecturer);
        userRepository.save(classRep);

        Software software = new Software("software1");
        softwareRepository.save(software);

        Set<Software> softwareList = new HashSet<>();
        softwareList.add(software);

        Classroom classroom = new Classroom("A1", 10, 10, 10, true, softwareList);
        classroomRepository.save(classroom);

        ClassSession classSession = new ClassSession("Test", lecturer, classRep, classroom, LocalDateTime.now(), Duration.ofHours(1));
        classSessionRepository.save(classSession);
    }
}
