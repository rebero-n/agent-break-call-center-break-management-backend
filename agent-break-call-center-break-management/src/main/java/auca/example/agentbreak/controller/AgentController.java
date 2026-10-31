package auca.example.agentbreak.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import auca.example.agentbreak.model.Agent;
import auca.example.agentbreak.service.AgentService;
@RestController 
@RequestMapping("/api/agents")

public class AgentController {
    private final AgentService agentService;
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveAgent(@RequestBody Agent agent) {
        return new ResponseEntity<>(agentService.saveAgent(agent), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAgentById(@PathVariable UUID id) {
        Optional<Agent> agent = agentService.getAgentById(id);
        if (agent.isEmpty()) {
            return new ResponseEntity<>("The agent with that id does not exist", HttpStatus.NOT_FOUND);        
        }
        return ResponseEntity.ok(agent.get());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAgent(@PathVariable UUID id, @RequestBody Agent agent) {
        String message = agentService.updateAgent(id,agent);
        if (message.equals("The agent with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAgent(@PathVariable UUID id) {
        String message = agentService.deleteAgent(id);
        if (message.equals("The agent with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Agent>> getAgentsByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(agentService.getAgentsByLastName(lastName));
    }
    @GetMapping("/of-supervisor/{supervisorId}")
    public ResponseEntity<?> getAgentsOfSupervisor(@PathVariable UUID supervisorId) {
        if (!agentService.supervisorExists(supervisorId)) {
            return new ResponseEntity<>("The supervisor with that id does not exist", HttpStatus.NOT_FOUND);

        }
        return ResponseEntity.ok(agentService.getAgentsOfSupervisor(supervisorId));
    }
    
}
