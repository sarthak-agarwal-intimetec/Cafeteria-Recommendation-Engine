package src.main.java.com.cafeteria;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            initializeStreams();
            while (true) {
                handleClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void initializeStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void handleClient() throws IOException {
        String employeeId = in.readLine();
        String name = in.readLine();
        String userRole = Database.getUserRole(employeeId);
        out.println(userRole);

        System.out.println("Received login request: " + userRole + ", " + employeeId + ", " + name);

        User user = createUser(employeeId, name, userRole);

        if (user != null && user.login(employeeId, name)) {
            out.println("Login successful as " + user.getRole());
            Database.addLoginActivity(user.getEmployeeId(), "Logged in");
            processCommands(user);
        } else {
            out.println("Invalid credentials");
        }
    }

    private User createUser(String employeeId, String name, String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(employeeId, name, "Admin");
            case "chef":
                return new Chef(employeeId, name, "Chef");
            case "employee":
                return new Employee(employeeId, name, "Employee");
            default:
                return null;
        }
    }

    private void processCommands(User user) throws IOException {
        CommandHandler handler = createCommandHandler(user);

        String command;
        while ((command = in.readLine()) != null) {
            if (command.toLowerCase().equals("l")) {
                Database.addLoginActivity(user.getEmployeeId(), "Logged out");
                break;
            }
            handler.handleCommand(command);
            Database.addLoginActivity(user.getEmployeeId(), command);
        }
    }

    private CommandHandler createCommandHandler(User user) {
        switch (user.getRole().toLowerCase()) {
            case "admin":
                return new AdminCommandHandler(in, out, user);
            case "chef":
                return new ChefCommandHandler(in, out, user);
            case "employee":
                return new EmployeeCommandHandler(in, out, user);
            default:
                throw new IllegalArgumentException("Unknown role: " + user.getRole());
        }
    }

    private void closeConnection() {
        try {
            System.out.println("Closing connection with " + socket.getRemoteSocketAddress());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
