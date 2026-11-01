package auca.example.agentbreak.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import auca.example.agentbreak.model.Team;
import java.util.Optional;
public interface TeamRepository extends JpaRepository<Team, UUID>{
    @Query("""
        SELECT t FROM Team t
        WHERE NOT EXISTS (SELECT s FROM Supervisor s WHERE s.team = t)
        ORDER BY t.teamNumber ASC
        """)

        List<Team> findFreeTeams();
        
Optional<Team> findByTeamNumber(int teamNumber);
}
