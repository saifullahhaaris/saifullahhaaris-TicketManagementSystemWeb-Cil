package com.example.ticketManagementSystem.controller;

import com.example.ticketManagementSystem.entity.Ticket;
import com.example.ticketManagementSystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST controller is to manage tickets in the Ticket Management System.
 * Provides endpoints to interact with the ticket service, including adding/retrieving tickets,
 * starting/stopping simulations, and accessing the logs.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /** Endpoint to get all tickets.
     * @return A list of all tickets.
     */
    @GetMapping("/getAllTickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    /** Endpoint to add a new ticket.
     * @param ticket The ticket to add.
     * @return The added ticket.
     */
    @PostMapping("/addTicket")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }

    /** Endpoint to get the number of available tickets.
     * @return The number of available tickets.
     */
    @GetMapping("/available-tickets")
    public int getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }
    /** Endpoint to start the ticket management simulation.
     * @param totalTickets The total number of tickets.
     * @param vendorCount The number of vendors.
     * @param customerCount The number of customers.
     * @param vendorRate The rate at which vendors add tickets.
     * @param customerRate The rate at which customers retrieve tickets.
     */
    @PostMapping("/start-simulation")
    public void startSimulation(@RequestParam int totalTickets,
                                @RequestParam int vendorCount,
                                @RequestParam int customerCount,
                                @RequestParam int vendorRate,
                                @RequestParam int customerRate) {
        ticketService.startSimulation(totalTickets, vendorCount, customerCount, vendorRate, customerRate);
    }

    @PostMapping("/stop-simulation")
    public void stopSimulation() {
        ticketService.stopSimulation();
    }

    @GetMapping("/simulation-status")
    public String getSimulationStatus() {
        return ticketService.getSimulationStatus();
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return ticketService.getLogs();
    }

    @GetMapping("/simulation-tickets")
    public List<Ticket> getSimulationTickets() {
        return ticketService.getSimulationTickets();
    }
}









