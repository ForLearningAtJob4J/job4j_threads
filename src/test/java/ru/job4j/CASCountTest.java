package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void when0AndIncrementThen1() {
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(1, counter.get());
    }
}