package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenTransferFrom1With100To2With200Then1With50And2With250() {
        UserStorage storage = new UserStorage();

        assertTrue(storage.add(new User(1, 100)));
        assertTrue(storage.add(new User(2, 200)));

        assertTrue(storage.transfer(1, 2, 50));
        assertEquals(50, storage.getAmountById(1));
        assertEquals(250, storage.getAmountById(2));
    }

    @Test
    public void when1With100And2With200AndDelete1ThenSizeEquals1And2WithAmount200() {
        UserStorage storage = new UserStorage();

        User user1 = new User(1, 100);
        assertTrue(storage.add(user1));
        assertTrue(storage.add(new User(2, 200)));

        assertTrue(storage.delete(user1));
        assertEquals(1, storage.size());
        assertEquals(200, storage.getAmountById(2));
    }

    @Test
    public void when1With100Update1WithAmount300ThenSizeEquals1And1WithAmount300() {
        UserStorage storage = new UserStorage();

        User user1 = new User(1, 100);
        assertTrue(storage.add(user1));
        assertTrue(storage.update(new User(1, 300)));

        assertEquals(1, storage.size());
        assertEquals(300, storage.getAmountById(1));
    }
}