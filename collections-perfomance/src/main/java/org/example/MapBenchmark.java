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
    private int size = 50_000;

    @Setup(Level.Invocation)
    public void setup() {

        hashMap = new HashMap<>();
        concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < size; i++) {
            hashMap.put(i, i);
            concurrentHashMap.put(i, i);
        }
    }

    @Benchmark
    public void hashMapPut() {
        hashMap.put(50_001, 1);
    }

    @Benchmark
    public void concurrentHashMapPut() {
        concurrentHashMap.put(50_001, 1);
    }
}
