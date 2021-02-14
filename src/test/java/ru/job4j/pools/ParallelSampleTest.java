package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelSampleTest {

    @Test
    public void whenLengthLessThan10AndExistsThenIndex() {
        String[] arr = new String[]{
                "Hello1", "John1", "Doe1",
                "Hello2", "John2", "Doe2",
                "Hello4", "John4", "Doe4"};
        assertEquals(Integer.valueOf(7), ParallelSample.indexOf(arr, "John4"));
    }

    @Test
    public void whenLengthMoreThan10AndExistsThenIndex() {
        String[] arr = new String[]{
                "Hello1", "John1", "Doe1",
                "Hello2", "John2", "Doe2",
                "Hello3", "John3", "Doe3",
                "Hello4", "John4", "Doe4"};
        assertEquals(Integer.valueOf(10), ParallelSample.indexOf(arr, "John4"));
    }

    @Test
    public void whenLengthLessThan10AndDoesNtExistThenNull() {
        String[] arr = new String[]{
                "Hello1", "John1", "Doe1",
                "Hello2", "John2", "Doe2",
                "Hello4", "John4", "Doe4"};
        assertNull(ParallelSample.indexOf(arr, "May"));
    }

    @Test
    public void whenLengthMoreThan10AndDoesNtExistsThenNull() {
        String[] arr = new String[]{
                "Hello1", "John1", "Doe1",
                "Hello2", "John2", "Doe2",
                "Hello3", "John3", "Doe3",
                "Hello4", "John4", "Doe4"};
        assertNull(ParallelSample.indexOf(arr, "May"));
    }
}