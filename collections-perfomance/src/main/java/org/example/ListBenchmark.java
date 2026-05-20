package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ListBenchmark {

    private List<Integer> arrayList;
    private List<Integer> linkedList;
    private int size = 50_000;
    private int operations = 3000;

    @Setup(Level.Invocation)
    public void setup() {

        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @Benchmark
    public void arrayListInsertMiddleSingle() {
        arrayList.add(size / 2, -1);
    }

    @Benchmark
    public void arrayListInsertMiddleMultiple() {
        for (int i = 0; i < operations; i++) {
            arrayList.add(size / 2, -1);
        }
    }

    @Benchmark
    public void arrayListInsertStartSingle() {
        arrayList.addFirst(-1);
    }

    @Benchmark
    public void arrayListInsertStartMultiple() {
        for (int i = 0; i < operations; i++) {
            arrayList.addFirst(-1);
        }
    }

    @Benchmark
    public void arrayListInsertLastSingle() {
        arrayList.addLast(-1);
    }

    @Benchmark
    public void arrayListInsertLastMultiple() {
        for (int i = 0; i < operations; i++) {
            arrayList.addLast(-1);
        }
    }

    @Benchmark
    public void linkedListInsertMiddleSingle() {
        linkedList.add(size / 2, -1);
    }

    @Benchmark
    public void linkedListInsertMiddleMultiple() {
        for (int i = 0; i < operations; i++) {
            linkedList.add(size / 2, -1);
        }
    }

    @Benchmark
    public void linkedListInsertStartSingle() {
        linkedList.addFirst(-1);
    }

    @Benchmark
    public void linkedListInsertStartMultiple() {
        for (int i = 0; i < operations; i++) {
            linkedList.addFirst(-1);
        }
    }

    @Benchmark
    public void linkedListInsertLastSingle() {
        linkedList.addLast(-1);
    }

    @Benchmark
    public void linkedListInsertLastMultiple() {
        for (int i = 0; i < operations; i++) {
            linkedList.addLast(-1);
        }
    }
}
