package com.example.ticketManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/** Entity class representing a Ticket in the Ticket Management System.
 * Uses JPA annotations for ORM mapping and Lombok annotations for boilerplate code reduction.
 */
@Entity
@Data
@Getter
public class Ticket {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketCode;
    private String status;
    private String priority;
    private boolean isSimulation = false;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    // Automatically sets the createdAt timestamp before persisting the entity.
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    // Getter and Setter for ticketCode
    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for priority
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Getter and Setter for isSimulation
    public boolean isSimulation() {
        return isSimulation;
    }

    public void setIsSimulation(boolean isSimulation) {
        this.isSimulation = isSimulation;
    }

    // Getter and Setter for createdAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}










