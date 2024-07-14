package src.main.java.com.cafeteria.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EmployeeService {

    public static void processCommand(String command, Scanner scanner, PrintWriter out, BufferedReader in)
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
                handleServerResponse(in, "Unknown command");
        }
    }

    public static void displayCommands() {
        System.out.println("Commands: ");
        System.out.println("1 - Display items rolled out by chef");
        System.out.println("2 - Vote for the Item for Next day");
        System.out.println("3 - Give feedback for the Item");
        System.out.println("4 - View Today's Notification");
        System.out.println("5 - Create/Update Profile");
        System.out.println("L - Logout");
    }

    public static String getUserInput(Scanner scanner, PrintWriter out, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        out.println(input);
        return input;
    }

    private static void handleViewDailyMenuItem(BufferedReader in) throws IOException {
        handleServerResponse(in, "Items Fetched Successfully");
    }

    private static void handleVote(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");

        handleServerResponse(in, "Item Voted Successfully");
    }

    private static void handleFeedback(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");
        getUserInput(scanner, out, "Enter item rating: ");
        getUserInput(scanner, out, "Enter item comment: ");

        handleServerResponse(in, "Feedback Submitted Successfully");
    }

    private static void handleViewNotification(BufferedReader in) throws IOException {
        handleServerResponse(in, "Notifications Fetched Successfully");
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

        handleServerResponse(in, "Profile Updated Successfully");
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
