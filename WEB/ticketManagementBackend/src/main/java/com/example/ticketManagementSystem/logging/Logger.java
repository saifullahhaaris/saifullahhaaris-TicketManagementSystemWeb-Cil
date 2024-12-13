package com.example.ticketManagementSystem.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final String LOG_FILE = "logs.txt";

    public static void log(String message) {
        String timeStampedMessage = LocalDateTime.now() + ": " + message;
        System.out.println(timeStampedMessage);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(timeStampedMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/** The above class provides logging functionality for the Ticket Management System.
 * It writes log messages to both the console and a file with a timestamp.
 * the log messages help in tracking the activities and debugging the application.
 */
