package auca.example.agentbreak.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;
public interface BreakRequestRepository extends JpaRepository<BreakRequest, UUID>{
    List<BreakRequest> findByStatusOrderByBreakDateAsc(BreakStatus status);

    List<BreakRequest> findByBreakDateBetweenOrderByBreakDateAsc(LocalDate start, LocalDate end);
}
