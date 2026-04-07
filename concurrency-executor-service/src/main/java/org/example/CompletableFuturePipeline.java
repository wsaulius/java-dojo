package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.IntStream;

public class CompletableFuturePipeline {

    private static final int TASK_COUNT = 100;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {

        // 🔹 Thread-safe queue (producer → consumers)
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);

        // 🔹 Thread-safe results storage
        ConcurrentLinkedQueue<Integer> results = new ConcurrentLinkedQueue<>();

        // 🔹 Thread pool for all async tasks
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        // 🔹 Supplier → generates random numbers
        Supplier<Integer> supplier = () ->
                ThreadLocalRandom.current().nextInt(1, 100);

        // 🔹 Function → defines computation logic
        Function<Integer, Integer> processor = value -> value * value;

        // 🔹 Consumer → processes value + stores result
        Consumer<Integer> consumer = value -> {

            int result = processor.apply(value);

            // store result safely
            results.add(result);

            System.out.println(
                    "Processed by " + Thread.currentThread().getName()
                            + " | value=" + value
                            + " | result=" + result
            );

            // simulate work
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Shared state
        AtomicInteger producedCount = new AtomicInteger(0);

        // 🔹 PRODUCER (CompletableFuture)
        CompletableFuture<Void> producerFuture = CompletableFuture.runAsync(() -> {
            try {
                while (producedCount.get() < TASK_COUNT) {

                    int value = supplier.get();

                    queue.put(value); // blocks if full

                    int count = producedCount.incrementAndGet();

                    System.out.println("Produced: " + value +
                            " | total=" + count);

                    // simulate periodic production (like scheduler)
                    Thread.sleep(50);
                }

                // 🔹 Send poison pills using Stream
                IntStream.range(0, THREAD_COUNT)
                        .forEach(i -> {
                            try {
                                queue.put(-1);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        });

                System.out.println("Producer finished");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, pool);

        // 🔹 CONSUMERS (multiple CompletableFutures using Stream)
        List<CompletableFuture<Void>> consumers =
                IntStream.range(0, THREAD_COUNT)
                        .mapToObj(i ->
                                CompletableFuture.runAsync(() -> {
                                    try {
                                        while (true) {

                                            Integer value = queue.take(); // blocking

                                            // poison pill → stop
                                            if (value == -1) {
                                                break;
                                            }

                                            consumer.accept(value);
                                        }

                                        System.out.println("Consumer done: "
                                                + Thread.currentThread().getName());

                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }

                                }, pool)
                        )
                        .toList();

        // 🔹 Wait for all consumers
        CompletableFuture<Void> allConsumers =
                CompletableFuture.allOf(consumers.toArray(new CompletableFuture[0]));

        // 🔹 Wait for everything to finish
        producerFuture.join();
        allConsumers.join();

        // 🔹 Shutdown thread pool
        pool.shutdown();

        // 🔹 Final aggregation using Stream
        int totalSum = results.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("\nTotal results: " + results.size());
        System.out.println("Final sum of squares: " + totalSum);
        System.out.println("All tasks completed!");
    }
}