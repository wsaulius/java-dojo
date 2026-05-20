package org.example;


import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunnerDemo {
    public static void main(String[] args) throws Exception {

        Options options = new OptionsBuilder()
                .include(ListBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
