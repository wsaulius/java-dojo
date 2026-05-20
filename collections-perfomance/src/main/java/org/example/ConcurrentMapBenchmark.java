package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ConcurrentMapBenchmark {

    private Map<Integer, Integer> hashMap;
    private Map<Integer, Integer> concurrentHashMap;

    @Setup(Level.Iteration)
    public void setup() {

        hashMap = new HashMap<>();
        concurrentHashMap = new ConcurrentHashMap<>();
    }

    @Benchmark
    @Threads(8)
    public void concurrentHashMapTest() {

        int key = (int) Thread.currentThread().threadId();

        concurrentHashMap.put(key, key);
        concurrentHashMap.get(key);
    }

    @Benchmark
    @Threads(8)
    public void hashMapTest() {

        int key = (int) Thread.currentThread().threadId();

        hashMap.put(key, key);
        hashMap.get(key);
    }
}
