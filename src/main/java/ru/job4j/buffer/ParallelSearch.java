package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        final int TIMEOUT = 1000;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2, TIMEOUT);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            Thread.sleep(TIMEOUT);
                            Integer i = queue.poll();
                            System.out.println(i);
                            if (i == null) {
                                throw new InterruptedException("Yeah");
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }
}