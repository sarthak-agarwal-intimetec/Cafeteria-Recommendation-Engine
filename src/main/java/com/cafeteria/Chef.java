package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Chef extends User {
    public Chef(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            displayMenu();

            String command = getUserInput(scanner, "Enter command: ");
            out.println(command);

            switch (command.toLowerCase()) {
                case "showrecommendation":
                    handleShowRecommendation(in);
                    break;
                case "rolloutmenu":
                    handleRollOutMenu(scanner, out, in);
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
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Commands: ");
        System.out.println("ShowRecommendation - Show Recommendation");
        System.out.println("RollOutMenu - Roll Out Menu");
        System.out.println("ViewDiscardMenuItem - View Discard Menu Item");
        System.out.println("RemoveDiscardMenuItem - Delete Item in Discard Menu items");
        System.out.println("DiscardMenuItemNotification - Send Notification to users to know more about improvements to be done for selected food item.");
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void handleShowRecommendation(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleRollOutMenu(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String itemNumber = getUserInput(scanner, "Enter item number for next day: ");
        out.println(itemNumber);

        handleServerResponse(in, "Item added successfully");
    }

    private static void handleViewDiscardMenuItem(BufferedReader in) throws IOException {
        handleServerResponse(in, "Item fetched successfully");
    }

    private static void handleRemoveDiscardMenuItem(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String itemId = getUserInput(scanner, "Enter item Id: ");
        out.println(itemId);

        handleServerResponse(in, "Item Deleted successfully");
    }

    private static void handleDiscardMenuItemNotification(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        String itemId = getUserInput(scanner, "Enter item Id: ");
        out.println(itemId);

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
