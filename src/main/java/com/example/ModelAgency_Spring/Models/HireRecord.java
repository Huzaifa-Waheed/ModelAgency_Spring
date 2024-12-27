package com.example.ModelAgency_Spring.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HireRecords")
public class HireRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "modelId", referencedColumnName = "modelId")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "userId")
    private User client;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;

    private double amount;

    @Column(length = 50)
    private String state;

    private LocalDateTime stateDate;

    @Lob
    private String description;

    private LocalDateTime requestedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getClient(){
        return client;
    }

    public void setClient(User client){
        this.client = client;
    }

    public Model getModel(){
        return model;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDateTime stateDate) {
        this.stateDate = stateDate;
    }
}

