package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);
    private final List<Thread> threads = new LinkedList<>() {
        {
            IntStream.range(0, SIZE).mapToObj(i -> new Thread(() -> {
                try {
                    Runnable myJob = tasks.poll();
                    myJob.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            })).forEach(threads::add);
        }
    };

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}