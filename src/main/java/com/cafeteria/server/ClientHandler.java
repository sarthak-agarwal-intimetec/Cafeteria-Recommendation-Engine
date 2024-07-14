package src.main.java.com.cafeteria.server;

import java.io.*;
import java.net.Socket;

import src.main.java.com.cafeteria.dao.LoginActivityDAO;
import src.main.java.com.cafeteria.dao.UserDAO;
import src.main.java.com.cafeteria.factory.CommandHandlerFactory;
import src.main.java.com.cafeteria.factory.UserFactory;
import src.main.java.com.cafeteria.handler.CommandHandler;
import src.main.java.com.cafeteria.model.User;

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
        String userRole = UserDAO.getUserRole(employeeId, name);
        out.println(userRole);

        System.out.println("Received login request: " + userRole + ", " + employeeId + ", " + name);

        try {
            User user = UserFactory.createUser(employeeId, name, userRole);

            if (user.login(employeeId, name)) {
                out.println("Login successful as " + user.getRole());
                LoginActivityDAO.addLoginActivity(user.getEmployeeId(), "Logged in");
                processCommands(user);
            } else {
                out.println("Invalid credentials");
            }
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    private void processCommands(User user) throws IOException {
        String command;
        while ((command = in.readLine()) != null) {
            if (command.toLowerCase().equals("l")) {
                LoginActivityDAO.addLoginActivity(user.getEmployeeId(), "Logged out");
                break;
            }
            handleCommand(user, command);
        }
    }

    private void handleCommand(User user, String command) throws IOException {
        CommandHandler commandHandler = CommandHandlerFactory.createCommandHandler(user, in, out);
        commandHandler.handleCommand(command);
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
