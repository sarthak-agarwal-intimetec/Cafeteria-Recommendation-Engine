package src.main.java.com.cafeteria;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class Employee extends User {
    public Employee(String employeeId, String name, String role) {
        super(employeeId, name, role);
    }

    public static void showCommands(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            System.out.println("Commands: ");
            System.out.println("DailyMenuItem - Display items rolled out by chef");
            System.out.println("Vote - Vote for the Item for Next day");
            System.out.println("Feedback - Give feedbak for the Item");
            System.out.println("Notification - View Today's Notification");

            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            out.println(command);

            if ("DailyMenuItem".equalsIgnoreCase(command)) {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("Vote".equalsIgnoreCase(command)) {
                System.out.print("Enter item Id: ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item voted successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("Feedback".equalsIgnoreCase(command)) {
                System.out.print("Enter item Id: ");
                out.println(scanner.nextLine());

                System.out.print("Enter item rating: ");
                out.println(scanner.nextLine());

                System.out.print("Enter item comment: ");
                out.println(scanner.nextLine());

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item feedbacked successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }

            else if ("Notification".equalsIgnoreCase(command)) {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.equals("Item fetched successfully") || response.equals("Unknown command")) {
                        break;
                    }
                }
            }
        }
    }
}

