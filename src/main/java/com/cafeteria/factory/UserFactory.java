package src.main.java.com.cafeteria.factory;

import src.main.java.com.cafeteria.client.Admin;
import src.main.java.com.cafeteria.client.Chef;
import src.main.java.com.cafeteria.client.Employee;
import src.main.java.com.cafeteria.model.User;

public class UserFactory {
    public static User createUser(String employeeId, String name, String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(employeeId, name, "Admin");
            case "chef":
                return new Chef(employeeId, name, "Chef");
            case "employee":
                return new Employee(employeeId, name, "Employee");
            default:
                return new User(employeeId, name, "unknown");
        }
    }
}
