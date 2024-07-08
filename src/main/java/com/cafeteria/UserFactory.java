package src.main.java.com.cafeteria;

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
