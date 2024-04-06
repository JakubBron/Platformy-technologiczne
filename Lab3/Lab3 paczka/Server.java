import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        int port = 9797;

        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                System.out.println("Waiting for connection on port "+port);
                Socket fromClientSocket = serverSocket.accept();
                new Thread( new ServerThread(fromClientSocket)).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
