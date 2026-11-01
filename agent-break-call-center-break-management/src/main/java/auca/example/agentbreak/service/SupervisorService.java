package auca.example.agentbreak.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.Supervisor;
import auca.example.agentbreak.repository.SupervisorRepository;
import org.springframework.transaction.annotation.Transactional;
import auca.example.agentbreak.model.Queue;
import auca.example.agentbreak.model.Team;
import auca.example.agentbreak.repository.QueueRepository;
import auca.example.agentbreak.repository.TeamRepository;
@Service
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    private final QueueRepository queueRepository;
    private final TeamRepository teamRepository;

    public SupervisorService(
        SupervisorRepository supervisorRepository,
        QueueRepository queueRepository,
        TeamRepository teamRepository)

    {
        this.supervisorRepository = supervisorRepository;
        this.queueRepository = queueRepository;
        this.teamRepository = teamRepository;

    }
    public String saveSupervisor(Supervisor supervisor) {
        supervisorRepository.save(supervisor);
        return "Supervisor saved successfully";

    }
    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();

    }
    public Optional<Supervisor> getSupervisorById(UUID id){
        return supervisorRepository.findById(id);

    }
    public String updateSupervisor(UUID id, Supervisor details) {
        Optional<Supervisor> opt = supervisorRepository.findById(id);
        if (opt.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Supervisor supervisor = opt.get();
        supervisor.setFirstName(details.getFirstName());
        supervisor.setLastName(details.getLastName());
        supervisor.setEmail(details.getEmail());
        if (details.getTeam() != null){
            supervisor.setTeam(details.getTeam());

        }
        supervisorRepository.save(supervisor);
        return "Supervisor saved successfully";

   }
   public String deleteSupervisor(UUID id){
    Optional<Supervisor> opt = supervisorRepository.findById(id);
    if (opt.isEmpty()) {
        return "The supervisor with that id does not exist";

    }
    supervisorRepository.delete(opt.get());
    return "Supervisor deleted successfully";

   }
   public List<Supervisor> getSupervisorsByQueue(String name){
    return supervisorRepository.findSupervisorsByQueue(name);   }

    @Transactional 
    public String addQueue(UUID suoervisorId, UUID queueId) {
        Optional<Supervisor> supervisorOpt = supervisorRepository.findById(suoervisorId);
        if (supervisorOpt.isEmpty()) {
            return "The supervisor with taht id does not exist";
        }
        Optional<Queue> queueOpt = queueRepository.findById(queueId);
        if (queueOpt.isEmpty()) {
            return "That queue does not exist";

        }
        Supervisor supervisor = supervisorOpt.get();
        Queue queue = queueOpt.get();
        if (supervisor.getQueues().contains(queue)){
            return "The supervisor already has that queue";
        }
        supervisor.getQueues().add(queue);
        supervisorRepository.save(supervisor);
        return "Queue added to the supervisor";
    }
    @Transactional 
    public String changeTeam(UUID supervisorId, int teamNumber) {
        Optional<Supervisor> supervisorOpt = supervisorRepository.findById(supervisorId);
        if(supervisorOpt.isEmpty()) {
            return "The supervisor with that id does not exist";

        }
        Optional<Team> teamOpt = teamRepository.findByTeamNumber(teamNumber);
        if (teamOpt.isEmpty()) {
            return "The team with that team number does not exist";

        }
        Supervisor supervisor = supervisorOpt.get();
        Team team = teamOpt.get();
        boolean sameTeam = supervisor.getTeam() != null && supervisor.getTeam().getId().equals(team.getId());

        if(!sameTeam && supervisorRepository.existsByTeam(team)) {
            return "That team is taken by another supervisor";
        }
        supervisor.setTeam(team);
        supervisorRepository.save(supervisor);
        return "Team changed successfully";
    }
    
}
