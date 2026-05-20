package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    private Map<Integer, Integer> hashMap;
    private Map<Integer, Integer> concurrentHashMap;

    @Setup(Level.Invocation)
    public void setup() {

        hashMap = new HashMap<>();
        concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 100_000; i++) {
            hashMap.put(i, i);
            concurrentHashMap.put(i, i);
        }
    }

    @Benchmark
    public void hashMapPut() {
        hashMap.put(100_001, 1);
    }

    @Benchmark
    public void concurrentHashMapPut() {
        concurrentHashMap.put(100_001, 1);
    }
}
