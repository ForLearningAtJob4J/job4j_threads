package ru.job4j.pools;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Thread)
public class RowColSumBenchmark {
    static int[][] a1 = new int[][]{
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}};

    @Benchmark
    public void sumBenchmark(Blackhole blackhole) {
        blackhole.consume(RowColSum.sum(a1));
    }

    @Benchmark
    public void asyncSumTest(Blackhole blackhole) throws ExecutionException, InterruptedException {
        blackhole.consume(RowColSum.asyncSum(a1));
    }

    @Benchmark
    public void asyncSumAllOfTest(Blackhole blackhole) throws ExecutionException, InterruptedException {
        blackhole.consume(RowColSum.asyncSumAllOf(a1));
    }

    public static void main(String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(RowColSum.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}