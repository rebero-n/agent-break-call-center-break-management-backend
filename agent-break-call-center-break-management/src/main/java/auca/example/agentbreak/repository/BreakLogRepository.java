package auca.example.agentbreak.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import auca.example.agentbreak.model.BreakLog;
import auca.example.agentbreak.model.BreakRequest;
public interface BreakLogRepository extends JpaRepository<BreakLog, UUID> {
    @Query("""
        SELECT d FROM BreakLog d
        WHERE d.breakRequest.agent.agentCode = :agentCode
        ORDER BY d.loggedDate DESC
        """)
        List<BreakLog> findLogsByAgentCode(@Param("agentCode") String agentCode);

        boolean existsByBreakRequest(BreakRequest breakRequest);
}