package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int upperBound;

    public SimpleBlockingQueue(int upperBound) {
        this.upperBound = upperBound;
    }

    public synchronized void offer(T value) {
        try {
            if (queue.size() == upperBound) {
                wait();
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
            if (queue.size() == 0) {
                wait();
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