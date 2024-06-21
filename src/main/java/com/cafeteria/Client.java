package src.main.java.com.cafeteria;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 54321;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter Employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            //out.println(role);
            out.println(employeeId);
            out.println(name);
            
            String role = in.readLine();

            String serverResponse = in.readLine();
            if (serverResponse != null){
                System.out.println(serverResponse);

                if (serverResponse.startsWith("Login successful")) {
                    
                    if("Admin".equals(role)){
                        Admin.showCommands(scanner, out, in);
                    }

                    else if("Chef".equals(role)){
                        Chef.showCommands(scanner, out, in);
                    }

                    else if("Employee".equals(role)){
                        Employee.showCommands(scanner, out, in);
                    }
                }
            } else {
                System.out.println("No response from server. Connection may be closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
