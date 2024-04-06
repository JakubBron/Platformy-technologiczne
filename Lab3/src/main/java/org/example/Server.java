package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int port = 2137;
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server started on localhost:" + port + ". Waiting for users...");
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                Thread ConnectionHandler = new Thread(new ConnectionHandler(clientSocket));
                ConnectionHandler.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}