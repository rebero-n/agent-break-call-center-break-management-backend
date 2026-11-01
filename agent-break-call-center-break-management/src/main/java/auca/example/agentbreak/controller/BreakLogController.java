package auca.example.agentbreak.controller;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import auca.example.agentbreak.model.BreakLog;
import auca.example.agentbreak.service.BreakLogService;

@RestController 
@RequestMapping("/api/break-logs")

public class BreakLogController {
    private final BreakLogService breakLogService;
    public BreakLogController(BreakLogService breakLogService) {
        this.breakLogService = breakLogService;

    }
    @PostMapping("/save")
    public ResponseEntity<String> saveBreakLog(
        @RequestParam UUID breakRequestId,
        @RequestBody BreakLog breakLog
    ) {
        String message = breakLogService.saveBreakLog(breakRequestId, breakLog);
        if (message.equals("THe break request with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else if (message.equals("Break logs are only for TAKEN break requests")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @GetMapping("/agent/{agentCode}")
    public ResponseEntity<List<BreakLog>> getBreakasLogsByAgent(@PathVariable String agentCode) {
        return ResponseEntity.ok(breakLogService.getBreakLogsByAgentCode(agentCode));
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteBreakLog(@PathVariable UUID id) {
        String message = breakLogService.deleteBreakLog(id);
        if (message.equals("The break log with that id does not exist")){
             return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
        
    }
}
