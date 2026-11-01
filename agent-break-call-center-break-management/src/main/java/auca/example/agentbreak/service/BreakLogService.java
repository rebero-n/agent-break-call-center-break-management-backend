package auca.example.agentbreak.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.BreakLog;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;
import auca.example.agentbreak.repository.BreakLogRepository;
import auca.example.agentbreak.repository.BreakRequestRepository;
@Service 

public class BreakLogService {
    private final BreakLogRepository breakLogRepository;
    private final BreakRequestRepository breakRequestRepository;

    public BreakLogService(
        BreakLogRepository breakLogRepository,
        BreakRequestRepository breakRequestRepository
    ){
        this.breakLogRepository = breakLogRepository;
        this.breakRequestRepository = breakRequestRepository;
    }
    public String saveBreakLog(UUID breakRequestId, BreakLog breakLog) {
        Optional<BreakRequest> opt = breakRequestRepository.findById(breakRequestId);
        if (opt.isEmpty()) {
            return "The break request with that od does not exist";
        }
        BreakRequest breakRequest = opt.get();
        if (breakRequest.getStatus() !=BreakStatus.TAKEN) {
            return "Break logs are only for TAKEN break requests";
        }
        breakLog.setLoggedDate(LocalDate.now());
        breakLog.setBreakRequest(breakRequest);
        breakLogRepository.save(breakLog);
        return "Break log saved successfully";
    }
    public List<BreakLog> getBreakLogsByAgentCode(String agentCode) {
        return breakLogRepository.findLogsByAgentCode(agentCode);
    }
    public String deleteBreakLog(UUID id) {
        Optional<BreakLog> opt = breakLogRepository.findById(id);
        if(opt.isEmpty()) {
            return "The break log with that id does not exist";
        }
        breakLogRepository.delete(opt.get());
        return "Break log deleted successfully";

    }

}
