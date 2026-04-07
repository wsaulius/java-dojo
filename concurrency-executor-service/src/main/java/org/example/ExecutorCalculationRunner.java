package org.example;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExecutorCalculationRunner {

    private static final int TASK_COUNT = 100;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {

        // Shared queue (thread-safe)
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // 🔹 Supplier (Producer logic)
        Supplier<Integer> supplier = () -> {
            int value = ThreadLocalRandom.current().nextInt(1, 100);
            System.out.println("Produced: " + value);
            return value;
        };

        // 🔹 Consumer (Processing logic)
        Consumer<Integer> consumer = value -> {
            String threadName = Thread.currentThread().getName();

            int result = value * value;

            System.out.println(
                    "Consumed by " + threadName +
                            " | value=" + value +
                            " | result=" + result
            );

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Producer task
        Runnable producerTask = () -> {
            try {
                for (int i = 0; i < TASK_COUNT; i++) {
                    int value = supplier.get();
                    queue.put(value); // blocks if full
                }

                // poison pills to stop consumers
                for (int i = 0; i < THREAD_COUNT; i++) {
                    queue.put(-1);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Consumer task
        Runnable consumerTask = () -> {
            try {
                while (true) {
                    int value = queue.take(); // blocks if empty

                    if (value == -1) {
                        // poison pill → stop
                        break;
                    }

                    consumer.accept(value);
                }

                System.out.println(Thread.currentThread().getName() + " finished");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 🔹 Submit 1 producer
        executor.submit(producerTask);

        // 🔹 Submit multiple consumers
        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            executor.submit(consumerTask);
        }

        // 🔹 Shutdown
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("All work done!");
    }
}