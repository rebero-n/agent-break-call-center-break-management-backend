package auca.example.agentbreak.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;
import auca.example.agentbreak.repository.BreakRequestRepository;
@Service
public class BreakRequestService {
    private final BreakRequestRepository breakRequestRepository;
    public BreakRequestService(BreakRequestRepository breakRequestRepository) {
        this.breakRequestRepository = breakRequestRepository;

    }
    public String saveBreakRequest(BreakRequest breakRequest){
        breakRequestRepository.save(breakRequest);
        return "Break request saved successfully";

    }
    public List<BreakRequest> getAllBreakRequests(){
        return breakRequestRepository.findAll();
    }
    public Optional<BreakRequest> getBreakRequestById(UUID id) {
        return breakRequestRepository.findById(id);

    }
    public String updateBreakRequest(UUID id, BreakRequest details){
        Optional<BreakRequest> opt = breakRequestRepository.findById(id);
        if (opt.isEmpty()) {
            return "The break request with that id does not exist";


        }
        BreakRequest breakRequest = opt.get();
        breakRequest.setBreakDate(details.getBreakDate());
        breakRequest.setReason(details.getReason());
        breakRequest.setRequestedMinutes(details.getRequestedMinutes());
        if (details.getAgent() != null){
            breakRequest.setAgent(details.getAgent());

        }
        if (details.getSupervisor() != null){
            breakRequest.setSupervisor(details.getSupervisor());
        }
        breakRequestRepository.save(breakRequest);
        return "Break request updated successfully";
    }
    public String deleteBreakRequest(UUID id){
        Optional<BreakRequest> opt = breakRequestRepository.findById(id);
        if (opt.isEmpty()){
            return "The break request with that id does not exist";
        }
        breakRequestRepository.delete(opt.get());
        return "Break request deleted successfully";

    }
    public List<BreakRequest> getBreakRequestsByStatus(BreakStatus status) {
        return breakRequestRepository.findByStatusOrderByBreakDateAsc(status);
    }
    public List<BreakRequest> getBreakRequestsBetween(LocalDate start, LocalDate end) {
        return breakRequestRepository.findByBreakDateBetweenOrderByBreakDateAsc(start, end);
    }
}
