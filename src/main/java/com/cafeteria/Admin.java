package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
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
        System.out.println("ShowMenu - Show menu items");
        System.out.println("AddMenuItem - Add menu items");
        System.out.println("UpdateMenuItem - Update menu items");
        System.out.println("DeleteMenuItem - Delete menu items");
        System.out.println("ViewDiscardMenuItem - View Discard Menu Item");
        System.out.println("RemoveDiscardMenuItem - Delete Item in Discard Menu items");
        System.out.println(
                "DiscardMenuItemNotification - Send Notification to users to know more about improvements to be done for selected food item.");
        System.out.println("Logout - Logout");
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
            case "showmenu":
                handleShowMenu(in);
                break;
            case "addmenuitem":
                handleAddMenuItem(scanner, out, in);
                break;
            case "updatemenuitem":
                handleUpdateMenuItem(scanner, out, in);
                break;
            case "deletemenuitem":
                handleDeleteMenuItem(scanner, out, in);
                break;
            case "viewdiscardmenuitem":
                handleViewDiscardMenuItem(in);
                break;
            case "removediscardmenuitem":
                handleRemoveDiscardMenuItem(scanner, out, in);
                break;
            case "discardmenuitemnotification":
                handleDiscardMenuItemNotification(scanner, out, in);
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private static void handleShowMenu(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleAddMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item name: ");
        getUserInput(scanner, out, "Enter item price: ");
        getUserInput(scanner, out, "Is item available (true/false): ");

        handleServerResponse(in, "Item added successfully");
    }

    private static void handleUpdateMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");
        getUserInput(scanner, out, "Enter item name: ");
        getUserInput(scanner, out, "Enter item price: ");
        getUserInput(scanner, out, "Is item available (true/false): ");

        handleServerResponse(in, "Item updated successfully");
    }

    private static void handleDeleteMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        getUserInput(scanner, out, "Enter item Id: ");

        handleServerResponse(in, "Item Deleted successfully");
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
            if (!response.isBlank() && (response.equalsIgnoreCase(successMessage) || response.equalsIgnoreCase("Unknown command"))) {
                break;
            }
        }
    }
}
