package auca.example.agentbreak.model;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String agentCode;
    private String extension;
    public UUID getId() {return id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public String getLastName() {return lastName;}
     public void setLastName(String lastName) {this.lastName = lastName;}
     public String getAgentCode(){return agentCode;}
     public void setAgentCode(String agentCode) {this.agentCode = agentCode;}
     public String getExtension() {return extension;}
     public void setExtension(String extension){this.extension = extension;}
}       

