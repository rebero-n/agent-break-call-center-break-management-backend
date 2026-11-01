package auca.example.agentbreak.service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.BreakStatus;
import auca.example.agentbreak.repository.AgentRepository;
import auca.example.agentbreak.repository.BreakLogRepository;
import auca.example.agentbreak.repository.BreakRequestRepository;
import auca.example.agentbreak.repository.SupervisorRepository;
import auca.example.agentbreak.repository.TeamRepository;

@Service 
public class DashboardService{
    private final AgentRepository agentRepository;
    private final SupervisorRepository supervisorRepository;
    private final TeamRepository teamRepository;
    private final BreakRequestRepository breakRequestRepository;
    private final BreakLogRepository breakLogRepository;

    public  DashboardService(
        AgentRepository agentRepository,
        SupervisorRepository supervisorRepository,
        TeamRepository teamRepository,
        BreakLogRepository breakLogRepository,
        BreakRequestRepository breakRequestRepository

    ) {
        this.agentRepository = agentRepository;
        this.supervisorRepository = supervisorRepository;
        this.teamRepository = teamRepository;
        this.breakLogRepository = breakLogRepository;
        this.breakRequestRepository = breakRequestRepository;
    }

    public Map<String, Long> getSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        long freeTeams = teamRepository.findFreeTeams().size();
        summary.put("agents", agentRepository.count());
        summary.put("supervisors", supervisorRepository.count());
        summary.put("freeTeams", freeTeams);
        summary.put("breaksToday", breakRequestRepository.countByBreakDate(LocalDate.now()));
        summary.put("requested", breakRequestRepository.countByStatus(BreakStatus.REQUESTED));
        summary.put("breakLogs", breakLogRepository.count());

        return summary;

    }
}