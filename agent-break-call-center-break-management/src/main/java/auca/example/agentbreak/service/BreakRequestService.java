package auca.example.agentbreak.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;
import auca.example.agentbreak.repository.BreakLogRepository;
import auca.example.agentbreak.repository.BreakRequestRepository;

@Service 
public class BreakRequestService {
    private final BreakRequestRepository breakRequestRepository;
    private final BreakLogRepository breakLogRepository;

    public BreakRequestService(
        BreakRequestRepository breakRequestRepository,
        BreakLogRepository breakLogRepository
    ) {
        this.breakRequestRepository = breakRequestRepository;
        this.breakLogRepository = breakLogRepository;
    }
    public String saveBreakRequest(BreakRequest breakRequest) {
        if (breakRequest.getBreakDate().isBefore(LocalDate.now())) {
            return "Break request date cannot be in the past";

        }
        boolean busy = breakRequestRepository.existsBySupervisor_IdAndBreakDateAndStatusNot(
                breakRequest.getSupervisor().getId(),
                breakRequest.getBreakDate(),
                BreakStatus.DENIED
        );
        if(busy) {
            return "Supervisor already handles a break request on that date";
        }
        breakRequest.setStatus(BreakStatus.REQUESTED);
        breakRequestRepository.save(breakRequest);
        return "Break request saved successfully";
    }
    public List<BreakRequest> getAllBreakRequests() {
        return breakRequestRepository.findAll();
    }
    public Optional<BreakRequest> getBreakRequestById(UUID id) {
        return breakRequestRepository.findById(id);

    }
    public String updateBreakRequest(UUID id, BreakRequest details) {
        Optional<BreakRequest> opt = breakRequestRepository.findById(id);
        if (opt.isEmpty()) {
            return "The break request with that id does not exist";
        }
        BreakRequest breakRequest = opt.get();
        breakRequest.setBreakDate(details.getBreakDate());
        breakRequest.setReason(details.getReason());
        breakRequest.setRequestedMinutes(details.getRequestedMinutes());
        if(details.getAgent() !=null) {
            breakRequest.setAgent(details.getAgent());
        }
        if (details.getSupervisor() !=null) {
            breakRequest.setSupervisor(details.getSupervisor());
        }
        breakRequestRepository.save(breakRequest);
        return "Break request updated successfully";

    }
    public String deleteBreakRequest(UUID id){
        Optional<BreakRequest> opt= breakRequestRepository.findById(id);
        if(opt.isEmpty()) {
            return "The break request with that id does not exist";
        }
        BreakRequest breakRequest = opt.get();
        if (breakLogRepository.existsByBreakRequest(breakRequest)) {
            return "That break request has break logs, delete them first";
        }
        breakRequestRepository.delete(breakRequest);
        return "Break request deleted successfully";
    }

    public List<BreakRequest> getBreakRequestsByStatus(BreakStatus status) {
        return breakRequestRepository.findByStatusOrderByBreakDateAsc(status);
    }
    public List<BreakRequest> getBreakRequestsBetween(LocalDate start, LocalDate end) {
        return breakRequestRepository.findByBreakDateBetweenOrderByBreakDateAsc(start, end);
    }
    public String updateStatus(UUID id, BreakStatus newStatus){
        Optional <BreakRequest> opt = breakRequestRepository.findById(id);
        if (opt.isEmpty()) {
            return "The break request with that id does not exist";
        }
        BreakRequest breakRequest = opt.get();
        BreakStatus current = breakRequest.getStatus();
        boolean allowed = false;
        if(current == BreakStatus.REQUESTED && (newStatus == BreakStatus.APPROVED || newStatus == BreakStatus.DENIED)){
            allowed = true;
        }
        if (current == BreakStatus.APPROVED && (newStatus == BreakStatus.TAKEN || newStatus == BreakStatus.DENIED)){
            allowed = true;
        }
        if(!allowed){
            return "Cannot change status from "+ current + " to" + newStatus;
        }
        breakRequest.setStatus(newStatus);
        breakRequestRepository.save(breakRequest);
        return "Break request status updated successfully";
    }
    public List<Object[]> getStatusCounts() {
        return breakRequestRepository.findStatusCounts();
    }

    @Transactional public String cancelDay(UUID supervisorId, LocalDate date) {
        int count = breakRequestRepository.denyDay(supervisorId, date, BreakStatus.DENIED, BreakStatus.TAKEN);    
        return count +"break requests cancelled";
    }
    public Page<BreakRequest> getBreakRequestsPage(Pageable pageable) {
        return breakRequestRepository.findAll(pageable);
    }
}