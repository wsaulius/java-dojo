package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ScheduledExecutorSinglePool {

    private static final int TASK_COUNT = 100;      // total items to produce
    private static final int THREAD_COUNT = 10;     // total threads (1 producer + 9 consumers)
    private static final int PRODUCER_INTERVAL_MS = 50; // production interval

    public static void main(String[] args) throws InterruptedException {

        // Shared queue between producer and consumers
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        // Single scheduled executor
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_COUNT);

        // Supplier → produces random numbers
        Supplier<Integer> supplier = () -> ThreadLocalRandom.current().nextInt(1, 100);

        // Consumer → processes numbers
        Consumer<Integer> consumer = value -> {
            String threadName = Thread.currentThread().getName();
            int result = value * value; // example calculation
            System.out.println("Consumed by " + threadName + " | value=" + value + " | result=" + result);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150)); // simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        AtomicInteger producedCount = new AtomicInteger(0); // track produced items

        // Placeholder for the scheduled future to allow cancellation
        final ScheduledFuture<?>[] producerFutureHolder = new ScheduledFuture[1];

        // Producer task
        Runnable producerTask = () -> {
            String threadName = Thread.currentThread().getName();
            if (producedCount.get() >= TASK_COUNT) {
                System.out.println("Producer finished producing items by: " + threadName);

                // send poison pills to stop consumers
                for (int i = 0; i < THREAD_COUNT - 1; i++) {
                    try {
                        queue.put(-1); // poison pill
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // cancel this scheduled producer task
                producerFutureHolder[0].cancel(false);
                return;
            }

            int value = supplier.get();
            try {
                queue.put(value); // blocks if queue is full
                int count = producedCount.incrementAndGet();
                System.out.println("Produced: " + value + " | by: " + threadName + " | total produced: " + count);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Consumer task
        Runnable consumerTask = () -> {
            try {
                Integer value = queue.poll(); // non-blocking
                if (value != null) {
                    if (value == -1) {
                        // poison pill → consumer should stop
                        Thread.currentThread().interrupt(); // mark thread as done
                        return;
                    }
                    consumer.accept(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Schedule producer at fixed rate
        producerFutureHolder[0] = scheduler.scheduleAtFixedRate(
                producerTask,
                0,
                PRODUCER_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );

        // Schedule consumers repeatedly
        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            scheduler.scheduleAtFixedRate(
                    consumerTask,
                    0,
                    20, // consumers check queue every 20ms
                    TimeUnit.MILLISECONDS
            );
        }

        // Wait for all tasks to finish
        scheduler.awaitTermination(TASK_COUNT * PRODUCER_INTERVAL_MS / THREAD_COUNT + 5000, TimeUnit.MILLISECONDS);

        // Shutdown scheduler cleanly
        scheduler.shutdown();
        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        System.out.println("All tasks completed!");
    }
}