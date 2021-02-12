package ru.job4j.cache;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdded2ObjectAnd1FromThemChanged2TimeAndAfterDeleteThen1UpdatedNameAndLeft1() throws InterruptedException {
        Base item1 = new Base(1, 1);
        item1.setName("ITEM1");
        Base item2 = new Base(2, 1);
        item2.setName("ITEM2");

        Cache cache = new Cache();
        cache.add(item1);
        cache.add(item2);

//        Thread firstThread = new Thread(() -> {
            Base newBase = new Base(1, 1);
            newBase.setName("CHANGED");
            cache.update(newBase);
//        });

//        firstThread.start();
//        firstThread.join();

//        Thread secondThread = new Thread(() -> {
            Base newBase2 = new Base(1, 2);
            newBase2.setName("CHANGED2");
            cache.update(newBase2);
//        });

//        secondThread.start();
//        secondThread.join();

        assertEquals("CHANGED2", cache.get(1).getName());
        assertEquals("ITEM2", cache.get(2).getName());
        cache.delete(new Base(1, 1));
        assertNull(cache.get(1));
    }

    @Test(expected = OptimisticException.class)
    public void whenDifferentVersionThenException() throws InterruptedException {
        Base item1 = new Base(1, 1);
        item1.setName("ITEM1");

        Cache cache = new Cache();
        cache.add(item1);

//        Thread firstThread = new Thread(() -> {
            Base newBase = new Base(1, 1);
            newBase.setName("CHANGED");
            cache.update(newBase);
//        });

//        firstThread.start();
//        firstThread.join();

        AtomicInteger wasException = new AtomicInteger(0);
//        Thread secondThread = new Thread(() -> {
            Base newBase2 = new Base(1, 1);
            newBase2.setName("CHANGED");
//            try {
                cache.update(newBase2);
//            } catch (OptimisticException e) {
//                wasException.set(1);
//            }
//        });
//        secondThread.start();
//        secondThread.join();
//        assertEquals(1, wasException.get());
    }
}