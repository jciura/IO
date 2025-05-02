package pl.agh.edu.io.Classroom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDto>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDto> getClassroomById(@PathVariable long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/software/{softwareName}")
    public ResponseEntity<List<ClassroomDto>> getClassroomBySoftware(@PathVariable String softwareName) {
        return ResponseEntity.ok(classroomService.getClassroomsBySoftware(softwareName));
    }

    @PostMapping
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody ClassroomDto classroomDto) {
        return ResponseEntity.ok(classroomService.createClassroom(classroomDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClassroomDto> deleteClassroom(@PathVariable long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDto> updateClassroom(
            @PathVariable long id,
            @RequestBody ClassroomDto classroomDto) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, classroomDto));
    }

    @PostMapping("/{classroomId}/software/{softwareName}")
    public ResponseEntity<Void> addSoftwareToClassroom(
            @PathVariable int classroomId,
            @PathVariable String softwareName) {
        classroomService.addSoftwareToClassroom(classroomId, softwareName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classroomId}/software/{softwareName}")
    public ResponseEntity<Void> removeSoftwareFromClassroom(
            @PathVariable int classroomId,
            @PathVariable String softwareName) {
        classroomService.removeSoftwareFromClassroom(classroomId, softwareName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClassroomDto>> getClassroomsByAttributes(
            @RequestParam(required = false) String building,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Boolean hasComputers,
            @RequestParam(required = false) String softwareName) {

        List<ClassroomDto> classrooms = classroomService.getClassroomsByAttributes(building, number, floor, capacity, hasComputers, softwareName);
        return ResponseEntity.ok(classrooms);
    }

}
