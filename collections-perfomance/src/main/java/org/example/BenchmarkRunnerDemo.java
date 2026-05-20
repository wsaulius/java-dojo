package org.example;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jol.info.GraphLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BenchmarkRunnerDemo {
    public static void main(String[] args) throws Exception {

        //Benchmark tests
        Options options = new OptionsBuilder()
                .include(".*Benchmark.*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(options).run();

        //Memory usage
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 1000; i++) {
            arrayList.add(i);
            linkedList.add(i);
            hashMap.put(i,i);
            concurrentHashMap.put(i,i);
        }

        System.out.println("ArrayList:");
        System.out.println(
                GraphLayout.parseInstance(arrayList).toFootprint()
        );

        System.out.println("\nLinkedList:");
        System.out.println(
                GraphLayout.parseInstance(linkedList).toFootprint()
        );

        System.out.println("\nHashMap:");
        System.out.println(
                GraphLayout.parseInstance(hashMap).toFootprint()
        );

        System.out.println("\nConcurrentHashMap:");
        System.out.println(
                GraphLayout.parseInstance(concurrentHashMap).toFootprint()
        );
    }
}
