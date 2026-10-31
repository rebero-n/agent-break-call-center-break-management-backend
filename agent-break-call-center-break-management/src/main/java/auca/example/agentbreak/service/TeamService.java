package auca.example.agentbreak.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.Team;
import auca.example.agentbreak.repository.TeamRepository;

@Service 
public class TeamService{
    private final TeamRepository teamRepository;
    public TeamService (TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public String saveTeam(Team team){
        teamRepository.save(team);
        return "Team saved successfully";

    }
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    public Optional<Team> getTeamById(UUID id) {
        return teamRepository.findById(id);

    }
     public String updateTeam(UUID id, Team details) {
        Optional<Team> opt = teamRepository.findById(id);
        if (opt.isEmpty()) {
         return "The team with that id does not exist";

        }
        Team team = opt.get();
        team.setTeamNumber(details.getTeamNumber());
        team.setName(details.getName());
        teamRepository.save(team);
        return"Team updated successfully";
    }
     public String deleteTeam(UUID id) {
        Optional<Team> opt = teamRepository.findById(id);
        if (opt.isEmpty()) {
            return "The team with that id does not exist";
        }
        teamRepository.delete(opt.get());
        return "Team deleted successfully";
    }
    public List<Team> getFreeTeams(){
        return teamRepository.findFreeTeams();
    }
}

