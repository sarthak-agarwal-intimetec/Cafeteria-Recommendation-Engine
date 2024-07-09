package src.main.java.com.cafeteria.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.factory.EmployeeCommandFactory;
import src.main.java.com.cafeteria.model.User;

public class EmployeeCommandHandler extends CommandHandler {

    public EmployeeCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        try {
            Command cmd = EmployeeCommandFactory.createCommand(command, in, out, user);
            cmd.execute();
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }
}
