package ru.job4j;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StackTest {

    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.poll());
        assertEquals(Integer.valueOf(2), stack.poll());
        assertEquals(Integer.valueOf(1), stack.poll());
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals(Integer.valueOf(1), stack.poll());
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(Integer.valueOf(2), stack.poll());
        assertEquals(Integer.valueOf(1), stack.poll());
    }
}