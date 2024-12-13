/** interface defining operations for managing tickets in the Ticket Management System.
 * This interface ensures that any class implementing it will provide methods for adding and removing tickets.
 */

package core;

public interface TicketOperation {
    void addTickets(String ticket);
    String removeTicket();
}