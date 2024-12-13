/** Repository interface for Ticket entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * This interface enables interaction with the database for ticket-related operations.
 */

package com.example.ticketManagementSystem.repository;
import com.example.ticketManagementSystem.controller.TicketController;
import com.example.ticketManagementSystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findFirstByOrderByCreatedAtAsc();

    long countByStatus(String available);

    List<Ticket> findByIsSimulation(boolean isSimulation);
//    List<Ticket> simulationTickets = ticketRepository.findByIsSimulation(true);

}