package org.example;

public class ResultPrinter implements Runnable {
    private MyQueue<String> results;

    public ResultPrinter(MyQueue<String> results) {
        this.results = results;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1500);
                String result = results.get();
                if (result.equals(null)) {
                    break;
                }
                System.out.println(result);
            }
        } catch (InterruptedException e) {
            System.out.println("Result printer was interrupted");
        }
    }
}
