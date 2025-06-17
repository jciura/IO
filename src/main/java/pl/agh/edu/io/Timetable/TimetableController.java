package pl.agh.edu.io.Timetable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/timetable/upload")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            int count = timetableService.processCsv(file);
            return ResponseEntity.ok("Imported " + count + " records");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Failed to import CSV: " + e.getMessage());
        }
    }
}
