import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{
    private Socket fromClientSocket;
    private final String ready = "ready";
    private final String ready_for_messages = "ready for messages";
    private final String finish = "finish";
    private int n = 0;

    public ServerThread(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        try (Socket localSocket = fromClientSocket;
             OutputStream outputStream = localSocket.getOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
             InputStream inputStream = localSocket.getInputStream();
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

            System.out.println("Thread started");
            String str;
            objectOutputStream.writeObject(ready);
            while(true){
                this.n = (Integer) objectInputStream.readObject();
                if(this.n > 0) {
                    System.out.println("n:" + this.n);
                    break;
                }
            }
            Integer[] lst = new Integer[10];
            while(true) {
                lst = (Integer[]) objectInputStream.readObject();
                if (lst != null){
                    System.out.println("I got list of Integer");
                    break;
                }
            }
            objectOutputStream.writeObject(ready_for_messages);
            Message mes;
            for ( int i = 0; i< n; i++){
                mes = (Message) objectInputStream.readObject();
                Thread.sleep(1500);
                System.out.println("num: "+mes.getNumber()+", content: "+mes.getContent());
            }

            System.out.print("Random numbers: [");
            for(int i = 0; i < 10; i++){
                if(i!=9){
                    System.out.print(lst[i]+", ");
                }else{
                    System.out.print(lst[i]);
                }
            }
            System.out.print("]");
            objectOutputStream.writeObject(finish);

            while(true){
                str = (String) objectInputStream.readObject();
                if(str.equals("kill")) {
                    System.out.println("Thread end");
                    break;
                }
            }
            objectOutputStream.close();
            objectInputStream.close();
            localSocket.close();
        }catch (ClassNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
