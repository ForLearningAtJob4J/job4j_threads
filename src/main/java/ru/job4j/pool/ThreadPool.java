package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public void work(Runnable job) {
        if (threads.size() == 0) {
            IntStream.range(0, SIZE).mapToObj(i -> new Thread(() -> {
                try {
                    Runnable myJob = tasks.poll();
                    myJob.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            })).forEach(threads::add);
        }
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}