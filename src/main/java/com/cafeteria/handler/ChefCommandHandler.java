package src.main.java.com.cafeteria.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.factory.ChefCommandFactory;
import src.main.java.com.cafeteria.model.User;

public class ChefCommandHandler extends CommandHandler {

    public ChefCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        try {
            Command cmd = ChefCommandFactory.createCommand(command, in, out);
            cmd.execute();
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }
}