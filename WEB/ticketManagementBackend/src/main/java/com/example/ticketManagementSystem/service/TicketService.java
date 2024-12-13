/** Service class for managing tickets in the Ticket Management System.
 * This class provides methods to add, update, and retrieve tickets, as well as
 * methods to start and stop ticket simulations. It uses an ExecutorService to
 * manage concurrent operations for the simulation and logs activities for monitoring.
 */


package com.example.ticketManagementSystem.service;

import com.example.ticketManagementSystem.entity.Ticket;
import com.example.ticketManagementSystem.repository.TicketRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    private ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed
    private boolean simulationRunning = false;
    //private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    private static final Logger logger = LogManager.getLogger(TicketService.class);


    public List<Ticket> getAllTickets() {
        return ticketRepository.findByIsSimulation(false); // Fetch only real tickets
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public int getAvailableTickets() {
        return (int) ticketRepository.countByStatus("Available");
    }

    public List<Ticket> getSimulationTickets() {
        return ticketRepository.findByIsSimulation(true);
    }

    public String getSimulationStatus() {
        return simulationRunning ? "Simulation is currently running" : "Simulation is stopped";
    }

    /** retrieves the log entries from the log file.
     * @return A list of log entries.
     */
    public List<String> getLogs() {
        // Assuming you're using a file appender
        File logFile = new File("logs/app.log");
        List<String> logLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logLines.add(line);
            }
        } catch (IOException e) {
            logger.error("Error reading log file", e);
        }

        return logLines;
    }

    /** Updates an existing ticket with new information.
     * @param ticketId The ID of the ticket to update.
     * @param updatedTicket The new ticket information.
     * @return The updated ticket.
     * @throws ResourceNotFoundException if the ticket is not found.
     */
    public Ticket updateTicket(Long ticketId, Ticket updatedTicket) throws ResourceNotFoundException {
        Ticket existingTicket = ticketRepository.findById(ticketId).orElseThrow(() ->
                new ResourceNotFoundException("Ticket with ID " + ticketId + " not found"));

        existingTicket.setStatus(updatedTicket.getStatus());
        existingTicket.setPriority(updatedTicket.getPriority());
        return ticketRepository.save(existingTicket);
    }

    /** Starts the ticket management simulation.
     * @param totalTickets The total number of tickets.
     * @param vendorCount The number of vendors.
     * @param customerCount The number of customers.
     * @param vendorRate The rate at which vendors add tickets.
     * @param customerRate The rate at which customers retrieve tickets.
     */
    public void startSimulation(int totalTickets, int vendorCount, int customerCount, int vendorRate, int customerRate) {
        if (simulationRunning) {
            return;
        }
        simulationRunning = true;

        for (int i = 0; i < vendorCount; i++) {
            executorService.submit(() -> {
                while (simulationRunning) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketCode("SIM-" + System.nanoTime());
                    ticket.setStatus("Available");
                    ticket.setPriority("Medium");
                    ticket.setIsSimulation(true);
                    ticketRepository.save(ticket);

                    try {
                        Thread.sleep(vendorRate * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (int i = 0; i < customerCount; i++) {
            executorService.submit(() -> {
                while (simulationRunning) {
                    Ticket ticket = ticketRepository.findFirstByOrderByCreatedAtAsc();
                    if (ticket != null) {
                        ticketRepository.delete(ticket);
                    }

                    try {
                        Thread.sleep(customerRate * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }

    /** Stops the ticket management simulation and shuts down the project.
     */
    public void stopSimulation() {
        simulationRunning = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        List<Ticket> simulationTickets = ticketRepository.findByIsSimulation(true);
        ticketRepository.deleteAll(simulationTickets);
    }
}

