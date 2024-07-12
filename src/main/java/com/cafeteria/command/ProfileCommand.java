package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.UserDAO;
import src.main.java.com.cafeteria.model.User;

public class ProfileCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;
    private User user;

    public ProfileCommand(BufferedReader in, PrintWriter out, User user) {
        this.in = in;
        this.out = out;
        this.user = user;
    }

    @Override
    public void execute() throws IOException {
        int dietaryPreference = Integer.parseInt(in.readLine());
        int spiceLevel = Integer.parseInt(in.readLine());
        int cuisineType = Integer.parseInt(in.readLine());
        int isSweetTooth = Integer.parseInt(in.readLine());
        String userId = user.getEmployeeId();

        UserDAO.updateProfile(dietaryPreference, spiceLevel, cuisineType, isSweetTooth, userId);
        out.println("Item fetched successfully");
    }
}
