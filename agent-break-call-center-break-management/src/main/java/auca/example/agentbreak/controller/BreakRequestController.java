package auca.example.agentbreak.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.time.LocalDate;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;
import auca.example.agentbreak.service.BreakRequestService;

@RestController 
@RequestMapping("/api/break-requests")
public class BreakRequestController {
    private final BreakRequestService breakRequestService;
    public BreakRequestController(BreakRequestService breakRequestService) {
        this.breakRequestService = breakRequestService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveBreakRequest(@RequestBody BreakRequest breakRequest) {
        return new ResponseEntity<>(breakRequestService.saveBreakRequest(breakRequest), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<BreakRequest>> getAllBreakRequests() {
        return ResponseEntity.ok(breakRequestService.getAllBreakRequests());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBreakRequestById(@PathVariable UUID id) {
        Optional<BreakRequest> breakRequest = breakRequestService.getBreakRequestById(id);
        if(breakRequest.isEmpty()) {
            return new ResponseEntity<>("The break request with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(breakRequest.get());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBreakRequest(@PathVariable UUID id, @RequestBody BreakRequest breakRequest) {
        String message = breakRequestService.updateBreakRequest(id, breakRequest);
        if (message.equals("The break request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteBreakRequest(@PathVariable UUID id) {
        String message = breakRequestService.deleteBreakRequest(id);
        if (message.equals("The break request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<BreakRequest>> getBreakRequestsByStatus(@RequestParam BreakStatus status) {
        return ResponseEntity.ok(breakRequestService.getBreakRequestsByStatus(status));
    }
    @GetMapping("/between")
    public ResponseEntity<List<BreakRequest>> getBreakRequestsBetween(
        @RequestParam String start,
        @RequestParam String end
    ) {
        return ResponseEntity.ok(breakRequestService.getBreakRequestsBetween(LocalDate.parse(start), LocalDate.parse(end)));

    }

    
}
