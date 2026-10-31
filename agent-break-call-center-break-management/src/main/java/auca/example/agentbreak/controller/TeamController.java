package auca.example.agentbreak.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import auca.example.agentbreak.model.Team;
import auca.example.agentbreak.service.TeamService;

@RestController 
@RequestMapping ("/api/teams")


public class TeamController {
    private final TeamService teamService;
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveTeam(@RequestBody Team team){
        return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());    
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable UUID id){
        Optional<Team> team = teamService.getTeamById(id);
        if(team.isEmpty()){
            return new ResponseEntity<>("The team with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(team.get());    
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTeam(@PathVariable UUID id, @RequestBody Team team) {
        String message = teamService.updateTeam(id, team);
        if (message.equals("The team with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
     @GetMapping("/free")
    public ResponseEntity<List<Team>> getFreeTeams() {
        return ResponseEntity.ok(teamService.getFreeTeams());
    }
     @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable UUID id) {
        String message = teamService.deleteTeam(id);
        if (message.equals("The team with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
       
    }

