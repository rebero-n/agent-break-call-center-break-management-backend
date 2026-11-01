package auca.example.agentbreak.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        String message = breakRequestService.saveBreakRequest(breakRequest);
        if (message.equals("Break request date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }else if (message.equals("Supervisor already handles a break request on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
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
        }else if(message.equals("That break request has break logs, delete them first")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
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
    @PatchMapping ("/{id}/status")
    public ResponseEntity<String> updateStatus(
        @PathVariable UUID id,
        @RequestParam BreakStatus status
    ) {
        String message = breakRequestService.updateStatus(id, status);
        if (message.equals("The break request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.startsWith("Cannot cahnge status")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping ("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatusCounts() {
        return ResponseEntity.ok(breakRequestService.getStatusCounts());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(
        @RequestParam UUID supervisorId,
        @RequestParam String date
    ) {
        return ResponseEntity.ok(breakRequestService.cancelDay(supervisorId, LocalDate.parse(date)));
    }

    @GetMapping ("/page")
    public ResponseEntity<Page<BreakRequest>> getBreakRequestsPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "breakDate, desc") String sort
    ) {
        String[] parts = sort.split(",");
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, parts[0]));
        return ResponseEntity.ok(breakRequestService.getBreakRequestsPage(pageable));
    }

    
}
