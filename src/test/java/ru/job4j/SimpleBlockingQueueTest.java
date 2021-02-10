package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    private static class Consumer extends Thread {
        private final SimpleBlockingQueue<Integer> queue;
        private final int howManyTimesDoPoll;

        private Consumer(final SimpleBlockingQueue<Integer> queue, int howManyTimesDoPoll) {
            this.queue = queue;
            this.howManyTimesDoPoll = howManyTimesDoPoll;
        }

        @Override
        public void run() {
            for (int i = 0; i < howManyTimesDoPoll; i++) {
                queue.poll();
            }
        }
    }

    private static class Producer extends Thread {
        private final SimpleBlockingQueue<Integer> queue;
        private final Integer[] elements;

        private Producer(final SimpleBlockingQueue<Integer> queue, Integer... elements) {
            this.queue = queue;
            this.elements = elements;
        }

        @Override
        public void run() {
            for (Integer i : elements) {
                this.queue.offer(i);
            }
        }
    }

    @Test
    public void whenQueueUpperBoundIs2And4ElementsProducedAnd2PolledThen2PollsInMainAndQueueSizeIs0() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2, 500);
        Thread producer = new Producer(queue, 22, 12, 3, 42);
        Thread consumer = new Consumer(queue, 2);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertNotNull(queue.poll());
        assertNotNull(queue.poll());
        assertNull(queue.poll());
        assertEquals(0, queue.size());
    }

    @Test
    public void whenQueueUpperBoundIs2And4ElementsProducedAnd4ElementsPolledThenSizeIs0() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2, 500);
        Thread producer = new Producer(queue, 12, 21, 31, 14);
        Thread consumer = new Consumer(queue, 4);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(0, queue.size());
    }
}