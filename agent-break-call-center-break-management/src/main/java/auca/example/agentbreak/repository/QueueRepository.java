package auca.example.agentbreak.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import auca.example.agentbreak.model.Queue;
public interface QueueRepository extends JpaRepository<Queue, UUID> {
    @Query("SELECT q FROM Queue q WHERE q.supervisors IS EMPTY")
    List<Queue> findUnusedQueues();

}

