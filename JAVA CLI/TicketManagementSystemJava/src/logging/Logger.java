/** Logger class for logging messages with timestamps.
 * This class provides functionality to log messages to both the console and a log file.
 * It uses BufferedWriter and FileWriter to write log entries to a file named "logs.txt".
 */

package logging;

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