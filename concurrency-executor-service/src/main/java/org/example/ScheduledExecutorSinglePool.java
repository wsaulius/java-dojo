package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.*;
import java.util.stream.IntStream;

public class ScheduledExecutorSinglePool {

    private static final int TASK_COUNT = 100;
    private static final int THREAD_COUNT = 10;
    private static final int PRODUCER_INTERVAL_MS = 50;

    public static void main(String[] args) throws InterruptedException {

        // 🔹 Shared queue (producer → consumers)
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);

        // 🔹 Thread-safe results storage
        ConcurrentLinkedQueue<Integer> results = new ConcurrentLinkedQueue<>();

        // 🔹 Scheduler with fixed thread pool
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_COUNT);

        // 🔹 Supplier → produces random numbers
        Supplier<Integer> supplier = () ->
                ThreadLocalRandom.current().nextInt(1, 100);

        // 🔹 Function → defines processing logic
        Function<Integer, Integer> processor = value -> value * value;

        // 🔹 Consumer → processes and stores results
        Consumer<Integer> consumer = value -> {
            String threadName = Thread.currentThread().getName();

            int result = processor.apply(value);

            // Store result safely
            results.add(result);

            System.out.println("Consumed by " + threadName +
                    " | value=" + value +
                    " | result=" + result);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Track how many items produced
        AtomicInteger producedCount = new AtomicInteger(0);

        // 🔹 Clean replacement for array hack
        AtomicReference<ScheduledFuture<?>> producerFutureRef = new AtomicReference<>();

        // 🔹 Producer task
        Runnable producerTask = () -> {
            String threadName = Thread.currentThread().getName();

            // Stop condition
            if (producedCount.get() >= TASK_COUNT) {
                System.out.println("Producer finished producing items by: " + threadName);

                // 🔹 Send poison pills using Stream
                IntStream.range(0, THREAD_COUNT - 1)
                        .forEach(i -> {
                            try {
                                queue.put(-1);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        });

                // 🔹 Cancel scheduled producer cleanly
                producerFutureRef.get().cancel(false);
                return;
            }

            try {
                int value = supplier.get();

                queue.put(value); // blocks if full

                int count = producedCount.incrementAndGet();

                System.out.println("Produced: " + value +
                        " | by: " + threadName +
                        " | total produced: " + count);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Consumer task
        Runnable consumerTask = () -> {
            try {
                Integer value = queue.poll(); // non-blocking

                if (value != null) {

                    // Poison pill → stop this consumer execution
                    if (value == -1) {
                        return;
                    }

                    consumer.accept(value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 🔹 Schedule producer
        producerFutureRef.set(
                scheduler.scheduleAtFixedRate(
                        producerTask,
                        0,
                        PRODUCER_INTERVAL_MS,
                        TimeUnit.MILLISECONDS
                )
        );

        // 🔹 Schedule consumers using Stream
        IntStream.range(0, THREAD_COUNT - 1)
                .forEach(i ->
                        scheduler.scheduleAtFixedRate(
                                consumerTask,
                                0,
                                20,
                                TimeUnit.MILLISECONDS
                        )
                );

        // 🔹 Wait for completion
        scheduler.awaitTermination(
                TASK_COUNT * PRODUCER_INTERVAL_MS / THREAD_COUNT + 5000,
                TimeUnit.MILLISECONDS
        );

        // 🔹 Shutdown executor
        scheduler.shutdown();

        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        // 🔹 Final aggregation using Stream
        int totalSum = results.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("\nTotal results stored: " + results.size());
        System.out.println("Final sum of squares: " + totalSum);

        System.out.println("All tasks completed!");
    }
}