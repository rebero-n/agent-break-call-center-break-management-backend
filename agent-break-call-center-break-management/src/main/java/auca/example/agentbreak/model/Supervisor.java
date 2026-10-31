package auca.example.agentbreak.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "supervisor")
public class Supervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToMany
    @JoinTable(
            name = "supervisor_queue",
            joinColumns = @JoinColumn(name = "supervisor_id"),
        inverseJoinColumns = @JoinColumn(name = "queue_id")
    )

    private List<Queue> queues = new ArrayList<>();
    public UUID getId() {return id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public Team getTeam() {return team;}
    public void setTeam(Team team){this.team = team;}
    public List<Queue> getQueues() {return queues;}
    public void setQueues(List<Queue> queues) {this.queues = queues;}

    
}