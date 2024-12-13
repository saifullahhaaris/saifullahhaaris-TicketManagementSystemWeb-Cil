/** Vendor class that handles the addition of tickets to the TicketPool.
 * This class extends AbstractTicketHandler and implements Runnable to allow
 * concurrent ticket addition operations in a separate thread.
 */

package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;

public class Vendor extends AbstractTicketHandler implements Runnable {
    private final TicketPool ticketPool;
    private final int maxTicketCapacity;
    private final int ticketReleaseRate;
    private int ticketsAdded = 0;


    public Vendor(TicketPool ticketPool, int maxTicketCapacity, int ticketReleaseRate) {
        super(ticketPool);
        this.ticketPool = ticketPool;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketsAdded = ticketsAdded;
    }

    /** The main method that runs in a separate thread to continuously add tickets.
     * Logs the status of ticket addition and stops when the maximum capacity is reached.
     */
    @Override
    public void run() {
        while (ticketPool.getTicketCount() < maxTicketCapacity) {
            String ticket = generateTicket();
            ticketPool.addTickets(ticket);
            ticketsAdded++;
            Logger.log("Vendor added ticket: " + ticket + ". Current ticket count: " + ticketPool.getTicketCount());

            try {
                Thread.sleep(ticketReleaseRate * 500); // Adjust sleep time based on release rate
            } catch (InterruptedException e) {
                Logger.log("Vendor interrupted.");
                break;
            }
        }
        Logger.log("Vendor has finished adding tickets.");
    }

    private String generateTicket() {
        return "Ticket-" + System.nanoTime();
    }

    @Override
    public void handleTickets() {
        run();
    }
}


