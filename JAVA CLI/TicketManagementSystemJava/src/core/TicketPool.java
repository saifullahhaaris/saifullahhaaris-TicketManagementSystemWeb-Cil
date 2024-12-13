/** Class that manages a pool of tickets with synchronized operations for thread safety.
 * Implements the TicketOperation interface to provide methods for adding and removing tickets.
 */

package core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool implements TicketOperation {
    private final List<String> tickets;

    /** Constructor to initialize the ticket pool with a specified initial capacity.
     * Adds tickets to the pool when initializing.
     * @param initialCapacity The initial number of tickets to be added to the pool.
     */
    public TicketPool(int initialCapacity) {
        tickets = Collections.synchronizedList(new LinkedList<>());
        if (initialCapacity > 0) {
            for (int i = 0; i < initialCapacity; i++) {
                tickets.add("Ticket-" + System.nanoTime());
            }
        }
    }

    @Override
    public synchronized void addTickets(String ticket) {
        tickets.add(ticket);
    }

    /** Removes and returns a ticket from the pool in a thread-safe manner.
     * If the pool is empty, it returns null.
     * @return The ticket that was removed, or null if the pool is empty.
     */
    @Override
    public synchronized String removeTicket() {
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets.remove(0);
        }
    }

    public synchronized String retrieveTicket() {
        if (!tickets.isEmpty()) {
            return tickets.remove(0);
        }
        return null;
    }

    public int getTicketCount() {
        return tickets.size();
    }
}