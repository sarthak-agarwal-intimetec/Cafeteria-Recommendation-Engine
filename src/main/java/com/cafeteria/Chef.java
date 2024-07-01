package src.main.java.com.cafeteria;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Chef extends User {
    public Chef(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while(true) {
            System.out.println("Commands: ");
            System.out.println("ShowRecommendation - Show Recommendation");
            System.out.println("RollOutMenu - Roll Out Menu");
            System.out.println("ViewDiscardMenuItem - View Discard Menu Item");
            System.out.println("RemoveDiscardMenuItem - Delete Item in Discard Menu items");
            System.out.println("DiscardMenuItemNotification - Send Notification to users to know more about improvements to be done for selected food item.");
            
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            out.println(command);

            if ("ShowRecommendation".equalsIgnoreCase(command)) {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("RollOutMenu".equalsIgnoreCase(command)) {
                System.out.print("Enter item number for next day: ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item added successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("ViewDiscardMenuItem".equalsIgnoreCase(command)) {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("RemoveDiscardMenuItem".equalsIgnoreCase(command)) {
                System.out.print("Enter item Id: ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item Deleted successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("DiscardMenuItemNotification".equalsIgnoreCase(command)) {
                System.out.print("Enter item Id: ");
                out.println(scanner.nextLine());
            
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Notification sent successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }
        }
    }
}
