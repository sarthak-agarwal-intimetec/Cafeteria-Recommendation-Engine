package src.main.java.com.cafeteria.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.service.ChefService;

public class Chef extends User {
    public Chef(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            ChefService.displayCommands();
            String command = ChefService.getUserInput(scanner, out, "Enter command: ");
            if (!command.isBlank() && command.toLowerCase().equals("l")) {
                break;
            }
            ChefService.processCommand(command, scanner, out, in);
        }
    }
}
