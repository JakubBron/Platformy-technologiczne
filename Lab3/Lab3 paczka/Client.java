import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    public static void main(String []args) throws Exception {
        int portNumber = 9797;

        Scanner sc = new Scanner(System.in);

        String str;

        System.out.println("Client has started");

        Socket socket = new Socket("127.0.0.1", portNumber);

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        while(true){
            str = (String) objectInputStream.readObject();
            if(str.equals("ready")){
                System.out.println("ready");
                break;
            }
        }


        str = sc.nextLine();
        Integer n = Integer.parseInt(str);
        while(n < 2){
            System.out.println("Wrong n, please enter n >= 2");
            str = sc.nextLine();
            n = Integer.parseInt(str);
        }

        Integer[] lst = new Integer[10];

        for(int i =0; i< 10; i++){
            lst[i] = ThreadLocalRandom.current().nextInt(1, n + 1);
        }

        objectOutputStream.writeObject(n);
        objectOutputStream.writeObject(lst);

        while (true) {
            str = (String) objectInputStream.readObject();
            if(str.equals("ready for messages")){
                System.out.println("ready for messages");
                break;
            }
        }

        String output = "test message";

        for(int i = 0; i< n; i++){
            objectOutputStream.writeObject(new Message(i, output));
        }

        while (true) {
            str = (String) objectInputStream.readObject();
            if (str.equals("finish")) {
                System.out.println("finish");
                break;
            }
        }

        objectOutputStream.writeObject("kill");
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
