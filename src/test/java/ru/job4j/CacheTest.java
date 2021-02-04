package ru.job4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CacheTest {

    public CacheTest(Object[] param) {
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[10][];
    }

    @Test
    public void whenInstOf() {
        final Cache[] cache1 = {null};
        final Cache[] cache2 = {null};

        Thread first = new Thread(() -> {
            cache1[0] = Cache.instOf();
            System.out.println("In thread run" + cache1[0]);
        });
        Thread second = new Thread(() -> {
            cache2[0] = Cache.instOf();
            System.out.println("In thread run" + cache2[0]);
        });
        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Cache1: " + cache1[0]);
        System.out.println("Cache2: " + cache2[0]);
        assertEquals(cache1[0], cache2[0]);
        Cache.killOf();
    }
}