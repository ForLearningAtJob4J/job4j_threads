package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] ret = new Sums[size];
        for (int i = 0; i < size; i++) {
            ret[i] = new Sums();
            if (matrix[i].length != size) {
                throw new UnsupportedOperationException("Matrix must be square");
            }
            for (int j = 0; j < size; j++) {
                ret[i].rowSum += matrix[i][j];
                ret[i].colSum += matrix[j][i];
            }
        }
        return ret;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] ret = new Sums[size];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (matrix[i].length != size) {
                throw new UnsupportedOperationException("Matrix must be square");
            }
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            ret[key] = futures.get(key).get();
        }
        return ret;
    }

    public static Sums[] asyncSumAllOf(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] ret = new Sums[size];
        var cfs = new CompletableFuture[size];
        for (int i = 0; i < size; i++) {
            if (matrix[i].length != size) {
                throw new UnsupportedOperationException("Matrix must be square");
            }
            cfs[i] = getTask(matrix, i);
        }
        CompletableFuture.allOf(cfs).join();
        for (int i = 0; i < size; i++) {
            ret[i] = (Sums) cfs[i].get();
        }
        return ret;
    }

    private static CompletableFuture<Sums> getTask(int[][] data, int pos) {
        return CompletableFuture.supplyAsync(() -> {
            Sums ret = new Sums();
            for (int i = 0; i < data.length; i++) {
                ret.rowSum += data[pos][i];
                ret.colSum += data[i][pos];
            }
            return ret;
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}' + System.lineSeparator();
        }
    }
}