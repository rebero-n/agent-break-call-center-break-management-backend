package auca.example.agentbreak.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import auca.example.agentbreak.model.BreakRequest;
import auca.example.agentbreak.model.BreakStatus;

public interface BreakRequestRepository extends JpaRepository<BreakRequest, UUID> {
    List<BreakRequest> findByStatusOrderByBreakDateAsc(BreakStatus status);
    List<BreakRequest> findByBreakDateBetweenOrderByBreakDateAsc(LocalDate start, LocalDate end);
    boolean existsBySupervisor_IdAndBreakDateAndStatusNot(
        UUID supervisorId, LocalDate breakDate, BreakStatus status
    );

    @Query("""
       SELECT b.status, COUNT(b)
       FROM BreakRequest b
       GROUP BY b.status
        """)
    List<Object[]> findStatusCounts();

    @Modifying 
    @Query("""
        UPDATE BreakRequest b
        SET b.status = :denied
        WHERE b.supervisor.id = :supervisorId
        AND b.breakDate = :date
        AND b.status <> :taken
        """)
        int denyDay(
            @Param("supervisorId") UUID supervisorId,
            @Param("date") LocalDate date,
            @Param("denied") BreakStatus denied,
            @Param("taken") BreakStatus taken);

            long countByBreakDate(LocalDate breakDate);
            long countByStatus(BreakStatus status);


        
}
