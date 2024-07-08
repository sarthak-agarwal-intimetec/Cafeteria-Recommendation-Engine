package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Employee extends User {

    public Employee(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) {
        try {
            while (true) {
                displayCommands();
                String command = getUserInput(scanner, out, "Enter command: ");
                if (!command.isBlank() && command.toLowerCase().equals("logout")) {
                    break;
                }
                processCommand(command, scanner, out, in);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while processing the command: " + e.getMessage());
        }
    }

    private static void displayCommands() {
        System.out.println("Commands: ");
        System.out.println("1 - Display items rolled out by chef");
        System.out.println("2 - Vote for the Item for Next day");
        System.out.println("3 - Give feedback for the Item");
        System.out.println("4 - View Today's Notification");
        System.out.println("5 - Create/Update Profile");
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
                handleViewDailyMenuItem(in);
                break;
            case "2":
                handleVote(scanner, out, in);
                break;
            case "3":
                handleFeedback(scanner, out, in);
                break;
            case "4":
                handleViewNotification(in);
                break;
            case "5":
                handleProfile(scanner, out, in);
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private static void handleViewDailyMenuItem(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item Fetched Succesfully");
    }

    private static void handleVote(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");

        handleServerResponse(in, "Item Voted Successfully");
    }

    private static void handleFeedback(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");
        getUserInput(scanner, out, "Enter item rating: ");
        getUserInput(scanner, out, "Enter item comment: ");

        handleServerResponse(in, "Item Feedbacked Successfully");
    }

    private static void handleViewNotification(BufferedReader in) throws IOException {
        handleServerResponse(in, "Notification Fetched Succesfully");
    }

    private static void handleProfile(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Please select one: \n" +
                "1. Vegetarian \n" +
                "2. Non-Vegetarian \n" +
                "3. Eggetarian \n");
        getUserInput(scanner, out, "Please select your spice level: \n" +
                "1. High \n" +
                "2. Medium \n" +
                "3. Low \n");
        getUserInput(scanner, out, "What do you prefer most?\n" +
                "1. North Indian \n" +
                "2. South Indian \n" +
                "3. Other \n");
        getUserInput(scanner, out, "Do you have a sweet tooth? \n" +
                "1.Yes \n" +
                "2. No \n");

        handleServerResponse(in, "Profile Updated Succesfully");
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
