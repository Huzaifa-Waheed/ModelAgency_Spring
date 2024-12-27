package com.example.ModelAgency_Spring.Models.DTOs;

import java.time.LocalDateTime;

public class HireRecordDTO {
    private Integer modelId;
    private Integer clientId;
    private double amount;
    private String description;
    private LocalDateTime requestedDate;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "HireRecordDTO{" +
                "modelId=" + modelId +
                ", clientId=" + clientId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", requestedDate=" + requestedDate +
                '}';
    }
}

