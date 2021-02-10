package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int timeout;
    private final int upperBound;

    public SimpleBlockingQueue(int upperBound, int timeout) {
        this.upperBound = upperBound;
        this.timeout = timeout;
    }

    public synchronized void offer(T value) {
        try {
            long spentTime = System.currentTimeMillis();
            while (queue.size() == upperBound && System.currentTimeMillis() - spentTime < timeout) {
                wait(timeout / 10);
            }
            System.out.println("Offered " + value);
            queue.offer(value);
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized T poll() {
        T value = null;
        try {
            long spentTime = System.currentTimeMillis();
            while (queue.size() == 0 && System.currentTimeMillis() - spentTime < timeout) {
                wait(timeout / 10);
            }
            value = queue.poll();
            System.out.println("Polled " + value);
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return value;
    }

    public synchronized int size() {
        return queue.size();
    }
}