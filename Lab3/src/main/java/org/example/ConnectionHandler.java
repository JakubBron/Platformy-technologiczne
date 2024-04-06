package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    // sm = server message
    private final String smReady = "ready";
    private final String smReadyForMessages = "ready for messages";
    private final String smFinisted = "finished";


    public ConnectionHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            try {
                System.out.println("Client connected at "+ clientSocket.getInetAddress() + ":"+ clientSocket.getPort());
                // send smReady
                outputStream.writeObject(smReady);

                // read n - integer
                var n = (Integer) inputStream.readObject();

                // send smReadyForMessages
                outputStream.writeObject(smReadyForMessages);
                for(int i = 0; i < n; i++) {
                    Message message = (Message) inputStream.readObject();
                    System.out.println("Message received is: " + message);
                }

                // send smFinisted
                outputStream.writeObject(smFinisted);
                System.out.println("Connection to client at " + clientSocket.getInetAddress() + ":"+clientSocket.getPort() + " closed");
                close();
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SocketException e ) {
            if (e.getCause() == null) {
                System.out.println("Connection to client at " + clientSocket.getInetAddress()+":"+clientSocket.getPort()+" closed.");
            } else {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            inputStream.close();
            outputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
