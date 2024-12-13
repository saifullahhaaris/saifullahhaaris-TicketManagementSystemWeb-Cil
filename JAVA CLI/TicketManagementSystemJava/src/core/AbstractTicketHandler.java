/** Abstract class that serves as a base for handling ticket operations.
 * This class ensures that subclasses can manage the ticket pool and control the running state.
 */

package core;

public abstract class AbstractTicketHandler {
    protected final TicketPool ticketPool;
    protected volatile boolean running = true;

    public AbstractTicketHandler(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public abstract void handleTickets();

    public void stop() {
        running = false;
    }
}