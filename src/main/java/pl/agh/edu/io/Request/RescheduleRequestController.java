package pl.agh.edu.io.Request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reschedule")
public class RescheduleRequestController {
    private final RescheduleRequestService rescheduleRequestService;

    public RescheduleRequestController(RescheduleRequestService rescheduleRequestService) {
        this.rescheduleRequestService = rescheduleRequestService;
    }

    @PostMapping("/request/{userId}/single")
    public ResponseEntity<Void> createOneTimeRequest(@PathVariable long userId, @RequestBody RescheduleRequestDto rescheduleRequestDto) {
        rescheduleRequestService.createOneTimeRequest(rescheduleRequestDto, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/{userId}/multiple")
    public ResponseEntity<Void> createMultipleRequest(@PathVariable long userId, @RequestBody MultipleRescheduleRequestDto multipleRescheduleRequestDto) {
        //rescheduleRequestService.createMultipleRequest(multipleRescheduleRequestDto, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RescheduleRequestDto>> getRequestByUser(@PathVariable long userId) {
        List<RescheduleRequestDto> dto = rescheduleRequestService.getPendingRequests(userId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable Long id) {
        rescheduleRequestService.acceptRequest(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        rescheduleRequestService.rejectRequest(id);
        return ResponseEntity.ok().build();
    }
}
