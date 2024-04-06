package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String smReady = "ready";
        final String smReadyForMessages = "ready for messages";
        final String smFinished = "finished";
        final String host = "localhost";
        final int port = 2137;
        try
        {
            Socket socket = new Socket(host, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server at " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("Server response: " + inputStream.readObject());

            // get n - number of messages from console
            System.out.println("Enter number of messages: ");
            Scanner scanner = new Scanner(System.in);
            Integer n = scanner.nextInt();

            outputStream.writeObject(n);

            // check if server is ready for messages
            System.out.println("Server response: " + inputStream.readObject());

            // send n messages
            for (int i = 0; i < n; i++) {
                System.out.println("Enter message number " + i + ": ");
                String content = scanner.nextLine();
                Message message = new Message(i, content);
                outputStream.writeObject(message);
            }

            // print response from server
            System.out.println("Server response: " + inputStream.readObject());

            // closing connection
            System.out.println("Closing connection to server at " + socket.getInetAddress() + ":" + socket.getPort());
            inputStream.close();
            outputStream.close();
            socket.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
