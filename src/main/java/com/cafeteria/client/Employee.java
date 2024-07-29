package src.main.java.com.cafeteria.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.service.EmployeeService;

public class Employee extends User {

    public Employee(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) {
        try {
            while (true) {
                EmployeeService.displayCommands();
                String command = EmployeeService.getUserInput(scanner, out, "Enter command: ");
                if (!command.isBlank() && command.toLowerCase().equals("l")) {
                    break;
                }
                EmployeeService.processCommand(command, scanner, out, in);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while processing the command: " + e.getMessage());
        }
    }

}
