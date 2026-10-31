package auca.example.agentbreak.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import auca.example.agentbreak.model.Supervisor;
import auca.example.agentbreak.service.SupervisorService;
@RestController 
@RequestMapping("/api/supervisors")


public class SupervisorController {
    private final SupervisorService supervisorService;
    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveSupervisor(@RequestBody Supervisor supervisor) {
        return new ResponseEntity<>(supervisorService.saveSupervisor(supervisor), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return ResponseEntity.ok(supervisorService.getAllSupervisors());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupervisorById(@PathVariable UUID id) {
        Optional<Supervisor> supervisor = supervisorService.getSupervisorById(id);
        if (supervisor.isEmpty()) {
            return new ResponseEntity<>("The supervisor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(supervisor.get());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSupervisor(@PathVariable UUID id, @RequestBody Supervisor supervisor) {
        String message = supervisorService.updateSupervisor(id, supervisor);
        if (message.equals("The supervisor with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSupervisor(@PathVariable UUID id) {
        String message = supervisorService.deleteSupervisor(id);
        if (message.equals("The supervisor with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/by-queue")
    public ResponseEntity<List<Supervisor>> getSupervisorsByQueue(@RequestParam String name) {
        return ResponseEntity.ok(supervisorService.getSupervisorsByQueue(name));
    }
}

