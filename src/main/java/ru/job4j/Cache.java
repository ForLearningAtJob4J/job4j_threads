package ru.job4j;

public class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            System.out.println("From instOf: Is Null ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cache = new Cache();
            System.out.println("From instOf: " + cache);
        }
        System.out.println("From instOf wanna go: " + cache);
        return cache;
    }

    public static void killOf() {
        cache = null;
    }
}