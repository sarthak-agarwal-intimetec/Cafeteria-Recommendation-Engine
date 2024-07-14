package src.main.java.com.cafeteria.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.dao.LoginActivityDAO;
import src.main.java.com.cafeteria.factory.EmployeeCommandFactory;
import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.util.Constant;

public class EmployeeCommandHandler extends CommandHandler {

    public EmployeeCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        try {
            Command cmd = EmployeeCommandFactory.createCommand(command, in, out, user);
            cmd.execute();
            LoginActivityDAO.addLoginActivity(user.getEmployeeId(),
                    Constant.employeeCommandMap.get(Integer.parseInt(command)));
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }
}
