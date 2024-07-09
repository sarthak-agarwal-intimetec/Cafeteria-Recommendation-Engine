package src.main.java.com.cafeteria.factory;

import src.main.java.com.cafeteria.Admin;
import src.main.java.com.cafeteria.Chef;
import src.main.java.com.cafeteria.Employee;
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
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
