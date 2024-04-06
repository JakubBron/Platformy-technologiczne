import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Integer> resultQueue = new BlockingQueue<>();
        BlockingQueue<Integer> taskQueue = new BlockingQueue<>();

        int n = Integer.parseInt(args[0]);
        List<Worker> workerList = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            workerList.add(new Worker(i, taskQueue, resultQueue));
            threadList.add(new Thread(workerList.get(i)));
            threadList.get(i).start();
        }

        ResultPrinter resultPrinter = new ResultPrinter(resultQueue);
        Thread resultPrinterThread = new Thread(resultPrinter);
        resultPrinterThread.start();

        Scanner scanner = new Scanner(System.in);

        String currentTask;
        while (!(currentTask = scanner.nextLine()).equals("exit")){

            if (!isNumeric(currentTask)) continue;
            int currentValue = Integer.parseInt(currentTask);
            taskQueue.put(currentValue);
        } 

        resultQueue.cancel();
        taskQueue.cancel();

    }
    public static int fib(int n){

        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == 0 || i == 1) array.add(1);
            else array.add(array.get(i-1) + array.get(i-2));
        }
        return array.get(n-1);
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

class BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<T>();
    boolean isCancelled = false;

    public synchronized void put(T element) throws InterruptedException {

        queue.add(element);
        notify();
    }

    public synchronized T take() throws InterruptedException {

        if (isCancelled && queue.isEmpty()) return null;

        while(queue.isEmpty()) {
            wait(8000);
            if (isCancelled && queue.isEmpty()) return null;
        }

        T item = queue.remove();
        notify();
        return item;
    }

    public void cancel(){
        isCancelled = true;
    }
}

class Worker implements Runnable {

    private int id;
    BlockingQueue<Integer> sharedTaskQueue;
    BlockingQueue<Integer> sharedResultQueue;

    public Worker(int id, BlockingQueue<Integer> sharedTaskQueue, BlockingQueue<Integer> sharedResultQueue) {
        this.id = id;
        this.sharedTaskQueue = sharedTaskQueue;
        this.sharedResultQueue = sharedResultQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5000);
                Integer currentVal = sharedTaskQueue.take();
                if (currentVal == null) break;
                int res = Main.fib(currentVal);
                System.out.println("thread " + this.id + ": " + currentVal + "th - " + res );

                sharedResultQueue.put(res);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ResultPrinter implements Runnable {

    BlockingQueue<Integer> sharedResultQueue;

    public ResultPrinter(BlockingQueue<Integer> sharedResultQueue) {
        this.sharedResultQueue = sharedResultQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);

                Integer currentVal = sharedResultQueue.take();
                if (currentVal == null) break;
                System.out.println("result: " + currentVal);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

}