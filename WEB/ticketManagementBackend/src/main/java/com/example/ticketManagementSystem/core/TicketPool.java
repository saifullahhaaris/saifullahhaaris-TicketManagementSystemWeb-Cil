package com.example.ticketManagementSystem.core;

import com.example.ticketManagementSystem.entity.Ticket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** this class manages a pool of tickets with thread-safe operations.
 * It allows adding and retrieving tickets, and provides a count of available tickets inside the pool.
 */
public class TicketPool {
    private final List<Ticket> tickets;

    /**
     * creates new tickets and adds them to the pool if the initial capacity is greater than 0.
     * @param initialCapacity The initial number of tickets in the pool.
     */
    public TicketPool(int initialCapacity) {
        tickets = Collections.synchronizedList(new LinkedList<>());
        if (initialCapacity > 0) {
            for (int i = 0; i < initialCapacity; i++) {
                Ticket ticket = new Ticket();
                ticket.setTicketCode("Ticket-" + System.nanoTime());
                ticket.setStatus("Available");
                tickets.add(ticket);
            }
        }
    }

    /** Adds a new ticket to the pool.
     * this method is synchronized to ensure thread safety.
     * @param ticket The ticket to be added.
     */
    public synchronized void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    /** retrieves and removes a ticket from the pool.
     * This method is synchronized to ensure thread safety.
     * @return The retrieved ticket or null if the pool is empty.
     */
    public synchronized Ticket retrieveTicket() {
        if (!tickets.isEmpty()) {
            return tickets.remove(0);
        }
        return null;
    }
    /** Gets the count of tickets currently in the pool.
     * @return The number of tickets in the pool.
     */
    public int getTicketCount() {
        return tickets.size();
    }
}
