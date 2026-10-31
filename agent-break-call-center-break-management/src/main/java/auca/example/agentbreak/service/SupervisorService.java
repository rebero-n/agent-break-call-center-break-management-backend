package auca.example.agentbreak.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.Supervisor;
import auca.example.agentbreak.repository.SupervisorRepository;
@Service
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    public SupervisorService(SupervisorRepository supervisorRepository){
        this.supervisorRepository = supervisorRepository;

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
    
}
