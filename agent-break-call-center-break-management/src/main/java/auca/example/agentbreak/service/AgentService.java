package auca.example.agentbreak.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.Agent;
import auca.example.agentbreak.repository.AgentRepository;
import auca.example.agentbreak.repository.SupervisorRepository;
@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final SupervisorRepository supervisorRepository;

    public AgentService(AgentRepository agentRepository, SupervisorRepository supervisorRepository) {
        this.agentRepository = agentRepository;
        this.supervisorRepository = supervisorRepository;
    }

    public String saveAgent(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
  public Optional<Agent> getAgentById(UUID id) {
        return agentRepository.findById(id);
    }

    public String updateAgent(UUID id, Agent details) {
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Agent agent = opt.get();
        agent.setFirstName(details.getFirstName());
        agent.setLastName(details.getLastName());
        agent.setAgentCode(details.getAgentCode());
        agent.setExtension(details.getExtension());
        agentRepository.save(agent);
        return "Agent updated successfully";
    }
      public String deleteAgent(UUID id) {
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) {
            return "The agent with that id does not exist";
        }
        agentRepository.delete(opt.get());
        return "Agent deleted successfully";
    }
    public List<Agent> getAgentsByLastName(String lastName) {
        return agentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public boolean supervisorExists(UUID supervisorId) {
        return supervisorRepository.existsById(supervisorId);
    }
    public List<Agent> getAgentsOfSupervisor(UUID supervisorId) {
        return agentRepository.findAgentsOfSupervisor(supervisorId);
    }
}