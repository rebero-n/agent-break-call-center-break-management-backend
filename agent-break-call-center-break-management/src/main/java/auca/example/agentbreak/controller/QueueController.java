package auca.example.agentbreak.controller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import auca.example.agentbreak.model.Queue;
import auca.example.agentbreak.service.QueueService;

@RestController 
@RequestMapping("/api/queues")
public class QueueController{
    private final QueueService queueService;
    public QueueController(QueueService queueService){
        this.queueService = queueService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveQueue(@RequestBody Queue queue){
        return new ResponseEntity<>(queueService.saveQueue(queue), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Queue>>getAllQueues(){
        return ResponseEntity.ok(queueService.getAllQueues());

    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getQueueById(@PathVariable UUID id){
        Optional<Queue> queue = queueService.getQueueById(id);
        if (queue.isEmpty()){
            return new ResponseEntity<>("The queue with that id does not exist",HttpStatus.NOT_FOUND);

        }
        return ResponseEntity.ok(queue.get());

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQueue(@PathVariable UUID id, @RequestBody Queue queue){
        String message = queueService.updateQueue(id, queue);
        if(message.equals("The queue with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQueue(@PathVariable UUID id){
        String message = queueService.deleteQueue(id);
        if(message.equals("The queue with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
    @GetMapping ("/unused")
    public ResponseEntity<List<Queue>> getUnusedQueues() {
        return ResponseEntity.ok(queueService.getUnusedQueues());
    }
}