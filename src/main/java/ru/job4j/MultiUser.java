package ru.job4j;

public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started and sleep for 2 seconds");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    barrier.on();
                    System.out.println(Thread.currentThread().getName() + " finished");
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " finished");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}