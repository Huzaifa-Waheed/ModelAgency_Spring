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

}

