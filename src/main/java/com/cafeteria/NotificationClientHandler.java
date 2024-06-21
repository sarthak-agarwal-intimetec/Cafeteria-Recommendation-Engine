package src.main.java.com.cafeteria;
import java.io.*; 
import java.net.*; 
import java.util.*;

public class NotificationClientHandler extends Thread {
    private Socket clientSocket;

    public NotificationClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        // Handle client communication
    }
}