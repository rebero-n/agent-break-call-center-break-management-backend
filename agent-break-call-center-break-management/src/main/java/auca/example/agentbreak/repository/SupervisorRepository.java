package auca.example.agentbreak.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import auca.example.agentbreak.model.Supervisor;
import auca.example.agentbreak.model.Team;
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {
    @Query("""
        SELECT s FROM Supervisor s
        JOIN s.queues q
        WHERE LOWER(q.name) = LOWER(:name)
        """)
        List<Supervisor> findSupervisorsByQueue(@Param("name") String name);
    boolean existsByTeam(Team team);    
}