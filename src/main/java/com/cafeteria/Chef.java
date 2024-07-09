package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import src.main.java.com.cafeteria.model.User;

public class Chef extends User {
    public Chef(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            displayCommands();
            String command = getUserInput(scanner, out, "Enter command: ");
            if (!command.isBlank() && command.toLowerCase().equals("l")) {
                break;
            }
            processCommand(command, scanner, out, in);
        }
    }

    private static void displayCommands() {
        System.out.println("Commands: ");
        System.out.println("1 - Show Recommendation");
        System.out.println("2 - Roll Out Menu");
        System.out.println("3 - View Discard Menu Item");
        System.out.println("4 - Delete Item in Discard Menu items");
        System.out.println(
                "5 - Send Notification to users to know more about improvements to be done for selected food item.");
        System.out.println("L - Logout");
    }

    private static String getUserInput(Scanner scanner, PrintWriter out, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        out.println(input);
        return input;
    }

    private static void processCommand(String command, Scanner scanner, PrintWriter out, BufferedReader in)
            throws IOException {
        switch (command.toLowerCase()) {
            case "1":
                handleShowRecommendation(in);
                break;
            case "2":
                handleRollOutMenu(scanner, out, in);
                break;
            case "3":
                handleViewDiscardMenuItem(in);
                break;
            case "4":
                handleRemoveDiscardMenuItem(scanner, out, in);
                break;
            case "5":
                handleDiscardMenuItemNotification(scanner, out, in);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    private static void handleShowRecommendation(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleRollOutMenu(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item number for next day: ");

        handleServerResponse(in, "Item added successfully");
    }

    private static void handleViewDiscardMenuItem(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleRemoveDiscardMenuItem(Scanner scanner, PrintWriter out, BufferedReader in)
            throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");

        handleServerResponse(in, "Item Deleted successfully");
    }

    private static void handleDiscardMenuItemNotification(Scanner scanner, PrintWriter out, BufferedReader in)
            throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");

        handleServerResponse(in, "Notification sent successfully");
    }

    private static void handleServerResponse(BufferedReader in, String successMessage) throws IOException {
        String response = "";
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (!response.isBlank()
                    && (response.equalsIgnoreCase(successMessage) || response.equalsIgnoreCase("Unknown command"))) {
                break;
            }
        }
    }
}
