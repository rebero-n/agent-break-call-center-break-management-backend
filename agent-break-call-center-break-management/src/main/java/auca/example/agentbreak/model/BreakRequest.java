package auca.example.agentbreak.model;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity 
@Table(name = "break_request")


public class BreakRequest {
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate breakDate;
    @Enumerated(EnumType.STRING)
    private BreakStatus status;
    private String reason;
    private int requestedMinutes;
    @ManyToOne 
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @ManyToOne 
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;
    public UUID getId() {return id;}
    public LocalDate getBreakDate() {return breakDate;}
    public void setBreakDate(LocalDate breakDate){this.breakDate = breakDate;}
    public BreakStatus getStatus() {return status;}
    public void setStatus(BreakStatus status) {this.status = status;}
    public String getReason() {return reason;}
    public void setReason (String reason) {this.reason = reason;}
    public int getRequestedMinutes() {return requestedMinutes;}
    public void setRequestedMinutes(int requestedMinutes){this.requestedMinutes = requestedMinutes;}
    public Agent getAgent() {return agent;}
    public void setAgent(Agent agent){this.agent = agent;}
    public Supervisor getSupervisor() {return supervisor;}
    public void setSupervisor(Supervisor supervisor) {this.supervisor = supervisor;}
}

