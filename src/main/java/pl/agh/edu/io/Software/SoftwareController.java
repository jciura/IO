package pl.agh.edu.io.Software;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    private final SoftwareService softwareService;

    public SoftwareController(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    @GetMapping
    public List<Software> getAllSoftware() {
        return softwareService.getAllSoftware();
    }

    @PostMapping("/{name}")
    public ResponseEntity<Software> addSoftware(@PathVariable String name) {
        Software newSoftware = softwareService.addSoftware(name);
        return ResponseEntity.ok(newSoftware);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoftware(@PathVariable int id) {
        boolean deleted = softwareService.deleteSoftware(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
