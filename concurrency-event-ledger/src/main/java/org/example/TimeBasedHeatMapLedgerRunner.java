package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class TimeBasedHeatMapLedgerRunner {

    private static final int LIMIT = 10;
    private static final int TOTAL_ITEMS = 1000;
    private static final long BUCKET_MILLIS = 1000;

    record ConsumedRecord(int item, String threadName, long timestampMillis) {}

    static class State {
        boolean producerDone = false;
    }

    public static void main(String[] args) throws InterruptedException {

        final Deque<Integer> buffer = new ArrayDeque<>();
        final ReentrantLock lock = new ReentrantLock();

        final Condition notEmpty = lock.newCondition();
        final Condition notFull = lock.newCondition();

        final State state = new State();

        final ConcurrentHashMap<Integer, ConsumedRecord> consumedItems = new ConcurrentHashMap<>();
        final ConcurrentHashMap<String, LongAdder> totalByConsumer = new ConcurrentHashMap<>();

        final ConcurrentHashMap<String, ConcurrentHashMap<Long, LongAdder>> heatMap = new ConcurrentHashMap<>();

        final long startMillis = System.currentTimeMillis();

        Consumer<Integer> itemConsumer = item -> {

            String threadName = Thread.currentThread().getName();
            long now = System.currentTimeMillis();
            long bucket = (now - startMillis) / BUCKET_MILLIS;

            consumedItems.put(item, new ConsumedRecord(item, threadName, now));

            totalByConsumer
                .computeIfAbsent(threadName, k -> new LongAdder())
                .increment();

            heatMap
                .computeIfAbsent(threadName, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(bucket, k -> new LongAdder())
                .increment();

            System.out.println(
                threadName + " consumed item " + item + " in bucket " + bucket
            );
        };

        final Runnable producer = () -> {
            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    lock.lock();
                    try {
                        while (buffer.size() == LIMIT) {
                            notFull.await();
                        }

                        buffer.addLast(i);
                        System.out.println("Produced: " + i);
                        notEmpty.signal();
                    } finally {
                        lock.unlock();
                    }

                    randomPause();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.lock();
                try {
                    state.producerDone = true;
                    notEmpty.signalAll();
                } finally {
                    lock.unlock();
                }

                System.out.println(Thread.currentThread().getName() + " producer done");
            }
        };

        final Runnable consumerTask = () -> {
            try {
                while (true) {
                    int item;

                    lock.lock();
                    try {
                        while (buffer.isEmpty() && !state.producerDone) {
                            notEmpty.await();
                        }

                        if (buffer.isEmpty() && state.producerDone) {
                            System.out.println(Thread.currentThread().getName() + " consumer done");
                            return;
                        }

                        item = buffer.removeFirst();
                        notFull.signal();
                    } finally {
                        lock.unlock();
                    }

                    itemConsumer.accept(item);
                    randomPause();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread producerThread = new Thread(producer, "producer");
        Thread consumer1 = new Thread(consumerTask, "consumer-1");
        Thread consumer2 = new Thread(consumerTask, "consumer-2");

        producerThread.start();
        consumer1.start();
        consumer2.start();

        producerThread.join();
        consumer1.join();
        consumer2.join();

        printSummary(consumedItems, totalByConsumer, heatMap);
    }

    private static void printSummary(
        ConcurrentHashMap<Integer, ConsumedRecord> consumedItems,
        ConcurrentHashMap<String, LongAdder> totalByConsumer,
        ConcurrentHashMap<String, ConcurrentHashMap<Long, LongAdder>> heatMap
    ) {
        System.out.println("Consumed total = " + consumedItems.size());

        System.out.println("--- Total by consumer ---");
        totalByConsumer.forEach((consumer, count) ->
            System.out.println(consumer + " -> " + count.sum())
        );

        System.out.println("--- Top consumer ---");
        totalByConsumer.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue(
                java.util.Comparator.comparingLong(LongAdder::sum)
            ))
            .ifPresent(entry ->
                System.out.println(
                    entry.getKey() + " consumed the most: " + entry.getValue().sum()
                )
            );

        System.out.println("--- Time-based heat map ---");
        heatMap.forEach((consumer, buckets) -> {
            System.out.println(consumer + ":");
            buckets.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(bucketEntry ->
                    System.out.println(
                        "  second " + bucketEntry.getKey() +
                        " -> " + bucketEntry.getValue().sum()
                    )
                );
        });
    }

    private static void randomPause() {
        try {
            int millis = ThreadLocalRandom.current().nextInt(1, 10);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}