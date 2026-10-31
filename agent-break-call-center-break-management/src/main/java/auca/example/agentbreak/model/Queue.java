package auca.example.agentbreak.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "queue")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "queues")
    private List<Supervisor> supervisors = new ArrayList<>();

    public UUID getId() {return id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public List<Supervisor> getSupervisors() {return supervisors;}
    public void setSupervisors(List<Supervisor> supervisors) {this.supervisors = supervisors;}

}