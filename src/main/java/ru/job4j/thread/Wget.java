package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Wget analogue
 */
public class Wget implements Runnable {
    private static final int ONE_KI_B = 1024;
    private final String url;
    private final int speed;
    private final String fileName;

    /**
     *
     * @param url url to file
     * @param speed speed in KiB/s
     */
    public Wget(String url, int speed) throws MalformedURLException {
        this.url = url;
        this.speed = speed;
        URL u = new URL(url);
        Path fn = Path.of(u.getPath()).getFileName();
        fileName = fn == null ? "unknown" : fn.toString() + ".get";
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[ONE_KI_B];
            int totalBytesRead = 0;
            int bytesRead;
            long timeMillis = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, ONE_KI_B)) != -1) {
                long last = System.currentTimeMillis() - timeMillis;
                if (last < 1000 / speed) {
                    Thread.sleep(1000 / speed - last);
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                System.out.printf("\rCopied: %d KiB ", totalBytesRead / ONE_KI_B);
                timeMillis = System.currentTimeMillis();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://frolov-lib.ru/books/bsp/v14/dbsp14.zip";
        //"https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        if (args.length > 0) {
            url = args[0];
        }
        int speed = 100;
        if (args.length > 1) {
            speed = Integer.parseInt(args[1]);
        }
        Thread wget;
        try {
            wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
            System.out.print("\rDownloaded");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}