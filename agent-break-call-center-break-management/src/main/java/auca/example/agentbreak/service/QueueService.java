package auca.example.agentbreak.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import auca.example.agentbreak.model.Queue;
import auca.example.agentbreak.repository.QueueRepository;

@Service 
public class QueueService{
    private final QueueRepository queueRepository;
    public QueueService(QueueRepository queueRepository){
        this.queueRepository = queueRepository;
    }
    public String saveQueue(Queue queue){
        queueRepository.save(queue);
        return "Queue saved successfully";

    }
    public List<Queue> getAllQueues(){
        return queueRepository.findAll();
    }
    public Optional<Queue> getQueueById(UUID id){
        return queueRepository.findById(id);

    }
    public String updateQueue(UUID id, Queue details){
        Optional<Queue> opt = queueRepository.findById(id);
        if (opt.isEmpty()){
            return "The queue with that id does not exist";

        }
        Queue queue = opt.get();
        queue.setName(details.getName());
        queueRepository.save(queue);
        return "Queue updated successfully";

    }

    public String deleteQueue(UUID id) {
        Optional<Queue> opt = queueRepository.findById(id);
        if (opt.isEmpty()){
            return "The queue with that id does not exist";
        }
        queueRepository.delete(opt.get());
        return "Queue deleted successfully";

    }
    public List<Queue> getUnusedQueues() {
        return queueRepository.findUnusedQueues();
        
    }
}
