package src.main.java.com.cafeteria.factory;

import java.io.BufferedReader;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.handler.AdminCommandHandler;
import src.main.java.com.cafeteria.handler.ChefCommandHandler;
import src.main.java.com.cafeteria.handler.CommandHandler;
import src.main.java.com.cafeteria.handler.EmployeeCommandHandler;
import src.main.java.com.cafeteria.model.User;

public class CommandHandlerFactory {
    public static CommandHandler createCommandHandler(User user, BufferedReader in, PrintWriter out) {
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
}
