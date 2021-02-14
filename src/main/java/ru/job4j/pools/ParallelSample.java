package ru.job4j.pools;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSample<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T subject;

    public ParallelSample(T[] array, T subject) {
        this.array = array;
        this.subject = subject;
    }

    public static <T> Integer indexOf(T[] array, T subject) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSample<>(array, subject));
    }

    @Override
    protected Integer compute() {
        if (array == null || array.length == 0) {
            return null;
        }

        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (Objects.equals(array[i], subject)) {
                    return i;
                }
            }
            return null;
        }

        int mid = (array.length) / 2;
        ParallelSample<T> leftPart = new ParallelSample<>(Arrays.copyOfRange(array, 0, mid), subject);
        ParallelSample<T> rightPart = new ParallelSample<>(Arrays.copyOfRange(array, mid + 1, array.length), subject);
        leftPart.fork();
        rightPart.fork();
        Integer right = rightPart.join();
        if (right != null) {
            return right + mid + 1;
        }
        return leftPart.join();
    }
}