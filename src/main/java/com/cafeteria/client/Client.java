package src.main.java.com.cafeteria.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 54321;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String employeeId = getInput(scanner, "Enter Employee ID: ");
                String name = getInput(scanner, "Enter Name: ");

                authenticateUser(out, employeeId, name);

                String role = in.readLine();
                handleServerResponse(in, scanner, out, role);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void authenticateUser(PrintWriter out, String employeeId, String name) {
        out.println(employeeId);
        out.println(name);
    }

    private static void handleServerResponse(BufferedReader in, Scanner scanner, PrintWriter out, String role)
            throws IOException {
        String serverResponse = in.readLine();
        if (serverResponse != null) {
            System.out.println(serverResponse);

            if (serverResponse.startsWith("Login successful")) {
                showRoleCommands(scanner, out, in, role);
            }
        } else {
            System.out.println("No response from server. Connection may be closed.");
        }
    }

    private static void showRoleCommands(Scanner scanner, PrintWriter out, BufferedReader in, String role)
            throws IOException {
        switch (role) {
            case "Admin":
                Admin.showCommands(scanner, out, in);
                break;
            case "Chef":
                Chef.showCommands(scanner, out, in);
                break;
            case "Employee":
                Employee.showCommands(scanner, out, in);
                break;
            default:
                System.out.println("Unknown role: " + role);
        }
    }
}
