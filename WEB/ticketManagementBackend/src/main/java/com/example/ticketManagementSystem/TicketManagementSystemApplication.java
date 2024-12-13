package com.example.ticketManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * this is the main entry point of the system/ this is where application starts to run
 * this class contains the main method which uses Spring Boot to launch the application.
 */
@SpringBootApplication
public class TicketManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketManagementSystemApplication.class, args);
	}

}
