package src.main.java.com.cafeteria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 54321;

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Cafeteria Server is running...");

            while (true) {
                acceptClient(serverSocket);
            }
        } catch (IOException e) {
            handleException("Error starting server", e);
        }
    }

    private void acceptClient(ServerSocket serverSocket) {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
            new ClientHandler(clientSocket).start();
        } catch (IOException e) {
            handleException("Error accepting client connection", e);
        }
    }

    private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
