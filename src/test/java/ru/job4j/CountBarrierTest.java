package ru.job4j;

import org.junit.Test;
import ru.job4j.concurrent.ThreadState;

import static org.junit.Assert.*;

public class CountBarrierTest {
    @Test
    public void doTestWith2Threads() throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);

        Thread slave = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished");
                },
                "Slave"
        );
        slave.start();
        Thread.sleep(500);
        countBarrier.count();
        countBarrier.count();
        countBarrier.count();
        Thread.sleep(500);
        assertEquals(Thread.State.TERMINATED, slave.getState());
    }
}