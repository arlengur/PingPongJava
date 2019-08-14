package ru.arlen.pingpong;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1)
@State(value = Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ComparativeTest {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ComparativeTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void pingPongSynch() {
        new PingPongSync();
    }

    @Benchmark
    public void pingPongAtomic() {
        new PingPongAtomic();
    }

    @Benchmark
    public void pingPongPark() {
        new PingPongPark();
    }

    @Benchmark
    public void pingPongLock() {
        new PingPongLock();
    }
}
