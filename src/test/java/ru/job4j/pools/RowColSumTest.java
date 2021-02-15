package ru.job4j.pools;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class RowColSumTest {
    static String resultStr = "[Sums{rowSum=15, colSum=55}" + System.lineSeparator()
            + ", Sums{rowSum=40, colSum=60}" + System.lineSeparator()
            + ", Sums{rowSum=65, colSum=65}" +  System.lineSeparator()
            + ", Sums{rowSum=90, colSum=70}" + System.lineSeparator()
            + ", Sums{rowSum=115, colSum=75}" + System.lineSeparator()
            + "]";
    static int[][] a1 = new int[][]{
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}};

    @Test
    public void sumTest() {
        RowColSum.Sums[] result = RowColSum.sum(a1);
        assertEquals(resultStr, Arrays.toString(result));
    }

    @Test
    public void asyncSumTest() throws ExecutionException, InterruptedException {
        RowColSum.Sums[] result = RowColSum.asyncSum(a1);
        assertEquals(resultStr, Arrays.toString(result));
    }

    @Test
    public void asyncSumAllOfTest() throws ExecutionException, InterruptedException {
        RowColSum.Sums[] result = RowColSum.asyncSumAllOf(a1);
        assertEquals(resultStr, Arrays.toString(result));
    }
}