package pl.agh.edu.io.Timetable;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.agh.edu.io.Class.ClassSession;
import pl.agh.edu.io.Class.ClassSessionRepository;
import pl.agh.edu.io.Classroom.Classroom;
import pl.agh.edu.io.Classroom.ClassroomNotFoundException;
import pl.agh.edu.io.Classroom.ClassroomRepository;
import pl.agh.edu.io.Course.Course;
import pl.agh.edu.io.Course.CourseNotFoundException;
import pl.agh.edu.io.Course.CourseRepository;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class TimetableService {

    private final ClassSessionRepository classSessionRepo;
    private final ClassroomRepository classroomRepo;
    private final CourseRepository courseRepo;

    public TimetableService(ClassSessionRepository classSessionRepo, ClassroomRepository classroomRepo, CourseRepository courseRepo) {
        this.classSessionRepo = classSessionRepo;
        this.classroomRepo = classroomRepo;
        this.courseRepo = courseRepo;
    }

    @Transactional
    public int processCsv(MultipartFile file) throws Exception {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();


        List<ClassSession> toSave = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(parser)
                .build()) {
            String[] row;

            // assume first line is header
            reader.readNext();

            while ((row = reader.readNext()) != null) {
                ClassSession classSession = new ClassSession();

                String classroomBuilding = row[10];
                int classroomNumber = Integer.parseInt(row[11]);
                Classroom classroom = classroomRepo.findByBuildingAndNumber(classroomBuilding, classroomNumber)
                        .orElseThrow(() -> new ClassroomNotFoundException(classroomBuilding, classroomNumber));

                String courseName = row[3];
                Course course = courseRepo.findByName(courseName)
                        .orElseThrow(() -> new CourseNotFoundException(courseName));

                LocalDate startDate = LocalDate.parse(row[7], dateFormat);
                LocalTime startTime = LocalTime.parse(row[8], timeFormat);
                LocalTime endTime = LocalTime.parse(row[9], timeFormat);
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                long duration = java.time.Duration.between(startTime, endTime).toMinutes();

                classSession.setClassroom(classroom);
                classSession.setCourse(course);
                classSession.setDateTime(startDateTime);
                classSession.setDuration(duration);

                toSave.add(classSession);

                System.out.println(classSession);
            }
        } catch (CsvValidationException e) {
            throw new Exception("CSV format error: " + e.getMessage());
        }

        classSessionRepo.saveAll(toSave);
        return toSave.size();
    }
}
