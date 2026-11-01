package auca.example.agentbreak.model;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "break_log")


public class BreakLog {
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String breakType;
    private String notes;
    private int durationMinutes;
    private LocalDate loggedDate;

    @ManyToOne 
    @JoinColumn(name = "break_request_id")
    private BreakRequest breakRequest;

    public UUID getId() {return id;}
    public String getBreakType() {return breakType;}
    public void setBreakType(String breakType) {this.breakType = breakType;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public int getDurationMinutes() {return durationMinutes;}
    public void setDurationMinutes(int durationMinutes) {this.durationMinutes = durationMinutes;}
    public LocalDate getLoggedDate() {return loggedDate;}
    public void setLoggedDate(LocalDate loggedDate) {this.loggedDate = loggedDate;}
    public BreakRequest getBreakRequest() {return breakRequest;}
    public void setBreakRequest(BreakRequest breakRequest) {this.breakRequest = breakRequest;}
    
}
