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
                displayMenu();
                String command = getUserInput(scanner, out);
                processCommand(command, scanner, out, in);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while processing the command: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("Commands: ");
        System.out.println("DailyMenuItem - Display items rolled out by chef");
        System.out.println("Vote - Vote for the Item for Next day");
        System.out.println("Feedback - Give feedback for the Item");
        System.out.println("Notification - View Today's Notification");
        System.out.println("Profile - Create/Update Profile");
        System.out.print("Enter command: ");
    }

    private static String getUserInput(Scanner scanner, PrintWriter out) {
        String command = scanner.nextLine();
        out.println(command);
        return command;
    }

    private static void processCommand(String command, Scanner scanner, PrintWriter out, BufferedReader in)
            throws IOException {
        switch (command.toLowerCase()) {
            case "dailymenuitem":
                handleResponse(in);
                break;
            case "vote":
                handleVote(scanner, out, in);
                break;
            case "feedback":
                handleFeedback(scanner, out, in);
                break;
            case "notification":
                handleResponse(in);
                break;
            case "profile":
                handleProfile(scanner, out, in);
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private static void handleResponse(BufferedReader in) throws IOException {
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                break;
            }
        }
    }

    private static void handleVote(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        System.out.print("Enter item Id: ");
        out.println(scanner.nextLine());
        handleResponse(in);
    }

    private static void handleFeedback(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        System.out.print("Enter item Id: ");
        out.println(scanner.nextLine());
        System.out.print("Enter item rating: ");
        out.println(scanner.nextLine());
        System.out.print("Enter item comment: ");
        out.println(scanner.nextLine());
        handleResponse(in);
    }

    private static void handleProfile(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        System.out.print("Please select one: \n" +
                "1. Vegetarian \n" +
                "2. Non-Vegetarian \n" +
                "3. Eggetarian \n");
        out.println(scanner.nextLine());

        System.out.print("Please select your spice level: \n" +
                "1. High \n" +
                "2. Medium \n" +
                "3. Low \n");
        out.println(scanner.nextLine());

        System.out.print("What do you prefer most?\n" +
                "1. North Indian \n" +
                "2. South Indian \n" +
                "3. Other \n");
        out.println(scanner.nextLine());

        System.out.print("Do you have a sweet tooth? \n" +
                "1.Yes \n" +
                "2. No \n");
        out.println(scanner.nextLine());

        handleResponse(in);
    }
}
