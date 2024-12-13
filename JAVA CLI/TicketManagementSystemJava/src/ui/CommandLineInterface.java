/** CommandLineInterface class for configuring and starting the Ticket Management System.
 * This class provides methods to configure system settings and start the simulation.
 */

package ui;

import config.Configuration;
import core.TicketPool;
import logging.Logger;
import threads.Customer;
import threads.Vendor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineInterface {

    /** Configures the system by gathering user input for various settings.
     * @return A Configuration object with the settings provided by the user.
     */
    public static Configuration configureSystem() {
        Scanner scanner = new Scanner(System.in);

        int totalTickets = 0;
        int ticketReleaseRate = 0;
        int customerRetrievalRate = 0;
        int maxTicketCapacity = 0;

        boolean validInput = false;

        do {
            System.out.print("Enter total tickets (positive integer): ");
            try {
                totalTickets = scanner.nextInt();
                if (totalTickets <= 0) {
                    System.out.println("Total tickets must be a positive integer. Please enter again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.nextLine();
            }
        } while (!validInput);

        validInput = false;

        do {
            System.out.print("Enter ticket release rate (seconds, positive integer): ");
            try {
                ticketReleaseRate = scanner.nextInt();
                if (ticketReleaseRate <= 0) {
                    System.out.println("Ticket release rate must be a positive integer. Please enter again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.nextLine();
            }
        } while (!validInput);

        validInput = false;
        do {
            System.out.print("Enter customer retrieval rate (seconds, positive integer): ");
            try {
                customerRetrievalRate = scanner.nextInt();
                if (customerRetrievalRate <= 0) {
                    System.out.println("Customer retrieval rate must be a positive integer. Please enter again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.nextLine();
            }
        } while (!validInput);

        validInput = false;


        do {
            System.out.print("Enter maximum ticket capacity (positive integer, greater than total tickets): ");
            try {
                maxTicketCapacity = scanner.nextInt();
                if (maxTicketCapacity <= totalTickets) {
                    System.out.println("Maximum capacity must be a positive integer and greater than total tickets. Please enter again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.nextLine();
            }
        } while (!validInput);
        scanner.close();
        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    /** Starts the ticket management system with the provided configuration.
     * @param config The configuration settings for the system.
     */
    public static void startSystem(Configuration config) {
        TicketPool ticketPool = new TicketPool(config.getTotalTickets());
        Vendor vendor = new Vendor(ticketPool, config.getMaxTicketCapacity(), config.getTicketReleaseRate());
        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());

        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();

        System.out.println("Enter 'Ctrl+C' to end the simulation:");

        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Simulation stopped.");
            vendor.stop();
            customer.stop();
            vendorThread.interrupt();
            customerThread.interrupt();
        }
    }
}