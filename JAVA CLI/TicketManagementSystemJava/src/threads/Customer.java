/** Customer class that handles the retrieval of tickets from the TicketPool.
 * This class extends AbstractTicketHandler and implements Runnable to allow
 * concurrent ticket retrieval operations in a separate thread.
 */

package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;

public class Customer extends AbstractTicketHandler implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    /** Constructor to initialize the ticket pool and retrieval rate.
     * @param ticketPool The TicketPool instance.
     * @param customerRetrievalRate The rate at which the customer retrieves tickets.
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        super(ticketPool);
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /** The main method that runs in a separate thread to continuously retrieve tickets.
     * Logs the status of ticket retrieval and stops when no tickets are available.
     */
    @Override
    public void run() {
        while (true) {
            String ticket = ticketPool.retrieveTicket();
            if (ticket == null) {
                Logger.log("No tickets available.");
                if (ticketPool.getTicketCount() == 0) {
                    break; // Stop only if all tickets are exhausted
                }
            } else {
                Logger.log("Customer retrieved ticket: " + ticket + ". Remaining ticket count: " + ticketPool.getTicketCount());
            }

            try {
                Thread.sleep(customerRetrievalRate * 500); // Adjust sleep time based on retrieval rate
            } catch (InterruptedException e) {
                Logger.log("Customer interrupted.");
                break;
            }
        }
        Logger.log("Customer thread exiting.");
    }

    @Override
    public void handleTickets() {
        run();
    }
}