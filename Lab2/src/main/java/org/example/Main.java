package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MyQueue<String> results = new MyQueue<>();
        MyQueue<Integer> tasks = new MyQueue<>();

        for(int i=0; i<1000; i++)
        {
            tasks.add(i);
        }

        int threadsNumber = (args.length > 0) ? Integer.parseInt(args[0]) : 5;
        List<Thread> runningThreads = new ArrayList<>();

        for(int i = 0; i < threadsNumber; i++) {
            Worker worker = new Worker(tasks, results, i);
            Thread thread = new Thread(worker);
            runningThreads.add(new Thread(worker));
            runningThreads.get(i).start();
        }

        ResultPrinter resultPrinter = new ResultPrinter(results);
        Thread printerThread = new Thread(resultPrinter);
        printerThread.start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if(command.equals("exit"))
            {
                for (Thread thread : runningThreads) {
                    thread.interrupt();
                }
                printerThread.interrupt();
                results.cancel();
                tasks.cancel();
                break;
            }
            else
            {
                try {
                    var number = Integer.parseInt(command);
                    tasks.add(number);
                    System.out.println("[+] Added " + number + " to the queue");
                } catch (NumberFormatException e) {
                    System.out.println("[!] Please enter a valid number or 'exit'");
                }
            }
        }
    }
}