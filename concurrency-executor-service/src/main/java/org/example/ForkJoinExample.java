package org.example;

import java.util.concurrent.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.IntStream;

public class ForkJoinExample {

    private static final int TASK_COUNT = 100;
    private static final int THRESHOLD = 10;

    public static void main(String[] args) {

        // 🔹 Supplier → generate data using streams
        Supplier<List<Integer>> supplier = () ->
                IntStream.range(0, TASK_COUNT)
                        .map(i -> ThreadLocalRandom.current().nextInt(1, 100))
                        .boxed()
                        .toList();

        // 🔹 Function → processing logic
        Function<Integer, Integer> processor = value -> value * value;

        // 🔹 Consumer → logging
        Consumer<String> logger = System.out::println;

        List<Integer> numbers = supplier.get();

        ForkJoinPool pool = new ForkJoinPool(10);

        int result = pool.invoke(
                new FunctionalTask(numbers, 0, numbers.size(), processor, logger)
        );

        pool.shutdown();

        System.out.println("\nFinal sum = " + result);
    }

    static class FunctionalTask extends RecursiveTask<Integer> {

        private final List<Integer> data;
        private final int start, end;
        private final Function<Integer, Integer> processor;
        private final Consumer<String> logger;

        FunctionalTask(List<Integer> data, int start, int end,
                       Function<Integer, Integer> processor,
                       Consumer<String> logger) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.processor = processor;
            this.logger = logger;
        }

        @Override
        protected Integer compute() {

            int length = end - start;

            // 🔹 Base case → use STREAM here
            if (length <= THRESHOLD) {

                return IntStream.range(start, end)
                        .map(i -> {
                            int value = data.get(i);
                            int result = processor.apply(value);

                            logger.accept(Thread.currentThread().getName()
                                    + " | index=" + i
                                    + " | value=" + value
                                    + " | result=" + result);

                            return result;
                        })
                        .sum();
            }

            // 🔹 Recursive split (Fork/Join)
            int mid = start + length / 2;

            FunctionalTask left = new FunctionalTask(data, start, mid, processor, logger);
            FunctionalTask right = new FunctionalTask(data, mid, end, processor, logger);

            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();

            return leftResult + rightResult;
        }
    }
}