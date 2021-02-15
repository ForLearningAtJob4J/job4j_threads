package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        final int TIMEOUT = 1000;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2, TIMEOUT);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(500);
                            Integer i = queue.poll();
                            System.out.println(i);
                            if (i == null) {
                                Thread.currentThread().interrupt();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("consumer finished");
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 30; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("producer finished");
                }

        ).start();

        Thread.sleep(10_000);
        consumer.interrupt();
        System.out.println("main finished");
    }
}