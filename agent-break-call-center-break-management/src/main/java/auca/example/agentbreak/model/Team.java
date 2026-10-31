package auca.example.agentbreak.model;

import java.util.UUID;

import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
 import jakarta.persistence.Table;

 @Entity
 @Table(name = "team")
 public class Team  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
     private int teamNumber;
     private String name;

     public UUID getId() {return id;}
     public int getTeamNumber() {return teamNumber;}
     public void setTeamNumber(int teamNumber) {this.teamNumber = teamNumber;}
     public String getName() {return name;}
     public void setName(String name) {this.name = name;}

 }