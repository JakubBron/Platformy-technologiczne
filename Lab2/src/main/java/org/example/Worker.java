package org.example;

public class Worker implements Runnable {
    private int id;
    MyQueue<Integer> tasks;
    MyQueue<String> results;

    public Worker(MyQueue<Integer> tasks, MyQueue<String> results, int id) {
        this.tasks = tasks;
        this.results = results;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3500);
                Integer task = tasks.get();
                if (task == null) {
                    break;
                }
                boolean isPrime = isPrime(task);
                String result_ = "[Worker"+ id + "]: Argument "+task+ " is " + (isPrime ? "prime" : "not prime")  +".";
                results.add(result_);
            }
        } catch (InterruptedException e) {
            System.out.println("Worker " + id + " was interrupted");
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i*i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
