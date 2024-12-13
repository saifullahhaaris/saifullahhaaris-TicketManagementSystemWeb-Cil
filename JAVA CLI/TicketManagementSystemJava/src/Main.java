import config.Configuration;
import ui.CommandLineInterface;

public class Main {
    public static void main(String[] args) {
        Configuration config = CommandLineInterface.configureSystem();
        CommandLineInterface.startSystem(config);
    }
}

/**
 * Main class of the Ticket Management System.
 * This class is the entry point for the application.
 */