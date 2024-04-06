package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue<T> {
    private Queue<T> queue = new LinkedList<T>();
    boolean isCancelled = false;

    public synchronized void add(T element) throws InterruptedException{
        queue.add(element);
        notify();
    }

    public synchronized T get() throws InterruptedException {
        if(isCancelled && queue.isEmpty()) {
            return null;
        }

        while (queue.isEmpty()) {
            wait(5000);
            if(isCancelled && queue.isEmpty()) {
                return null;
            }
        }

        T item = queue.remove();
        notify();
        return item;

    }

    public void cancel() {
        isCancelled = true;
    }
}
