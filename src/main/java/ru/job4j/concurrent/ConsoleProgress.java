package ru.job4j.concurrent;

import static java.lang.Thread.*;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        int i = 0;
        try {
            while (!currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[i]);
                if (++i > 3) {
                    i = 0;
                }
                sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.printf("%nConsoleProgress interrupted");
        }
    }
}