package auca.example.agentbreak.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import auca.example.agentbreak.model.Agent;
public interface AgentRepository extends JpaRepository<Agent, UUID> {
    List<Agent> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    @Query("""
        SELECT DISTINCT a FROM BreakRequest b
        JOIN b.agent a
        WHERE b.supervisor.id = :supervisorId

        """)
        List<Agent> findAgentsOfSupervisor(@Param("supervisorId") UUID supervisorId);
}

