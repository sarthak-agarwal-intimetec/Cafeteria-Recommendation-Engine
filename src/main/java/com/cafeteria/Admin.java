package src.main.java.com.cafeteria;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }
    
    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            System.out.println("Commands: ");
            System.out.println("ShowMenu - Show menu items");
            System.out.println("AddMenuItem - Add menu items");
            System.out.println("UpdateMenuItem - Update menu items");
            System.out.println("DeleteMenuItem - Delete menu items");
            //System.out.println("UpdateUserInfo - Update User information");
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            out.println(command);

            if ("ShowMenu".equalsIgnoreCase(command)) {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("AddMenuItem".equalsIgnoreCase(command)) {
                System.out.print("Enter item name: ");
                out.println(scanner.nextLine());

                System.out.print("Enter item price: ");
                out.println(scanner.nextLine());

                System.out.print("Is item available (true/false): ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item added successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("UpdateMenuItem".equalsIgnoreCase(command)) {
                System.out.print("Enter item Id: ");
                out.println(scanner.nextLine());

                System.out.print("Enter item name: ");
                out.println(scanner.nextLine());

                System.out.print("Enter item price: ");
                out.println(scanner.nextLine());

                System.out.print("Is item available (true/false): ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item updated successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("DeleteMenuItem".equalsIgnoreCase(command)) {
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
        }
    }
}
