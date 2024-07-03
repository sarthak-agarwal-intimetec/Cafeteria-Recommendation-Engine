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
        while (true) {
            displayMenu();

            String command = getUserInput(scanner, "Enter command: ");
            out.println(command);

            if ("ShowMenu".equalsIgnoreCase(command)) {
                handleShowMenu(in);
            } else if ("AddMenuItem".equalsIgnoreCase(command)) {
                handleAddMenuItem(scanner, out, in);
            } else if ("UpdateMenuItem".equalsIgnoreCase(command)) {
                handleUpdateMenuItem(scanner, out, in);
            } else if ("DeleteMenuItem".equalsIgnoreCase(command)) {
                handleDeleteMenuItem(scanner, out, in);
            } else if ("ViewDiscardMenuItem".equalsIgnoreCase(command)) {
                handleViewDiscardMenuItem(in);
            } else if ("RemoveDiscardMenuItem".equalsIgnoreCase(command)) {
                handleRemoveDiscardMenuItem(scanner, out, in);
            } else if ("DiscardMenuItemNotification".equalsIgnoreCase(command)) {
                handleDiscardMenuItemNotification(scanner, out, in);
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Commands: ");
        System.out.println("ShowMenu - Show menu items");
        System.out.println("AddMenuItem - Add menu items");
        System.out.println("UpdateMenuItem - Update menu items");
        System.out.println("DeleteMenuItem - Delete menu items");
        System.out.println("ViewDiscardMenuItem - View Discard Menu Item");
        System.out.println("RemoveDiscardMenuItem - Delete Item in Discard Menu items");
        System.out.println("DiscardMenuItemNotification - Send Notification to users to know more about improvements to be done for selected food item.");
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void handleShowMenu(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleAddMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String name = getUserInput(scanner, "Enter item name: ");
        String price = getUserInput(scanner, "Enter item price: ");
        String availability = getUserInput(scanner, "Is item available (true/false): ");

        out.println(name);
        out.println(price);
        out.println(availability);

        handleServerResponse(in, "Item added successfully");
    }

    private static void handleUpdateMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String id = getUserInput(scanner, "Enter item Id: ");
        String name = getUserInput(scanner, "Enter item name: ");
        String price = getUserInput(scanner, "Enter item price: ");
        String availability = getUserInput(scanner, "Is item available (true/false): ");

        out.println(id);
        out.println(name);
        out.println(price);
        out.println(availability);

        handleServerResponse(in, "Item updated successfully");
    }

    private static void handleDeleteMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String id = getUserInput(scanner, "Enter item Id: ");
        out.println(id);

        handleServerResponse(in, "Item Deleted successfully");
    }

    private static void handleViewDiscardMenuItem(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleRemoveDiscardMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String id = getUserInput(scanner, "Enter item Id: ");
        out.println(id);

        handleServerResponse(in, "Item Deleted successfully");
    }

    private static void handleDiscardMenuItemNotification(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String id = getUserInput(scanner, "Enter item Id: ");
        out.println(id);

        handleServerResponse(in, "Notification sent successfully");
    }

    private static void handleServerResponse(BufferedReader in, String successMessage) throws IOException {
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (response.equals(successMessage) || response.equals("Unknown command")) {
                break;
            }
        }
    }
}
