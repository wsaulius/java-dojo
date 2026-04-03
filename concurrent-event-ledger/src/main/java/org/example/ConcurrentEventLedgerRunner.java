package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class ConcurrentEventLedgerRunner {

    private static final int LIMIT = 10;
    private static final int TOTAL_ITEMS = 100;

    enum Role {
        PRODUCER,
        CONSUMER
    }

    enum EventType {
        STARTED,
        WAITING_FOR_SPACE,
        WAITING_FOR_ITEM,
        PRODUCED,
        CONSUMED,
        DONE
    }

    // Use records: all state in one
    record PipelineEvent(
        Role role,
        String threadName,
        EventType eventType,
        Integer item,
        long timestamp
    ) {}

    static class State {
        boolean producerDone = false;
    }

    public static void main(String[] args) throws InterruptedException {

        final Deque<Integer> buffer = new ArrayDeque<>();
        final ReentrantLock lock = new ReentrantLock();

        // Condition Lock
        final Condition notEmpty = lock.newCondition();
        final Condition notFull = lock.newCondition();

        final State state = new State();

        // One concurrent structure for everything:
        final ConcurrentLinkedQueue<PipelineEvent> ledger = new ConcurrentLinkedQueue<>();

        final Consumer<Integer> itemConsumer = item -> {

            ledger.add(new PipelineEvent(
                Role.CONSUMER,
                Thread.currentThread().getName(),
                EventType.CONSUMED,
                item,
                System.nanoTime()
            ));

            System.out.println(
                Thread.currentThread().getName() + " consumed item " + item
            );
        };

        final Runnable producer = () -> {

            ledger.add(new PipelineEvent(
                Role.PRODUCER,
                Thread.currentThread().getName(),
                EventType.STARTED,
                null,
                System.nanoTime()
            ));

            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {

                    // Re-entrant lock
                    lock.lock();
                    try {
                        while (buffer.size() == LIMIT) {
                            ledger.add(new PipelineEvent(
                                Role.PRODUCER,
                                Thread.currentThread().getName(),
                                EventType.WAITING_FOR_SPACE,
                                null,
                                System.nanoTime()
                            ));

                            // Introduce await
                            notFull.await();
                        }

                        buffer.addLast( i );

                        ledger.add(new PipelineEvent(
                            Role.PRODUCER,
                            Thread.currentThread().getName(),
                            EventType.PRODUCED,
                            i,
                            System.nanoTime()
                        ));

                        // Introduce signal
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

                    // Introduce signal all
                    notEmpty.signalAll();

                } finally {
                    lock.unlock();
                }

                ledger.add(new PipelineEvent(
                    Role.PRODUCER,
                    Thread.currentThread().getName(),
                    EventType.DONE,
                    null,
                    System.nanoTime()
                ));

            }
        };

        Runnable consumerTask = () -> {
            ledger.add(new PipelineEvent(
                Role.CONSUMER,
                Thread.currentThread().getName(),
                EventType.STARTED,
                null,
                System.nanoTime()
            ));

            try {
                while (true) {
                    int item;

                    // Re-entrant
                    lock.lock();
                    try {
                        while (buffer.isEmpty() && !state.producerDone) {

                            ledger.add(new PipelineEvent(
                                Role.CONSUMER,
                                Thread.currentThread().getName(),
                                EventType.WAITING_FOR_ITEM,
                                null,
                                System.nanoTime()
                            ));
                            notEmpty.await();
                        }

                        if (buffer.isEmpty() && state.producerDone) {
                            ledger.add(new PipelineEvent(
                                Role.CONSUMER,
                                Thread.currentThread().getName(),
                                EventType.DONE,
                                null,
                                System.nanoTime()
                            ));
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

        printSummary(ledger);
    }

    private static void printSummary(ConcurrentLinkedQueue<PipelineEvent> ledger) {

        Map<String, EventType> latestState = new HashMap<>();
        Map<String, Integer> heatMap = new HashMap<>();
        int produced = 0;
        int consumed = 0;

        for (PipelineEvent event : ledger) {
            latestState.put(event.threadName(), event.eventType());

            if (event.eventType() == EventType.PRODUCED) {
                produced++;
            }

            if (event.eventType() == EventType.CONSUMED) {
                consumed++;
                heatMap.merge(event.threadName(), 1, Integer::sum);
            }
        }

        System.out.println("Produced total = " + produced);
        System.out.println("Consumed total = " + consumed);
        System.out.println("Ledger size = " + ledger.size());

        System.out.println("--- Latest worker state ---");
        for (Map.Entry<String, EventType> entry : latestState.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("--- Consumer heat map ---");
        for (Map.Entry<String, Integer> entry : heatMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Introduce var
        final var topConsumer = heatMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        topConsumer.ifPresent(e ->
                System.out.println("Top consumer: " + e.getKey() + ", count=" + e.getValue())
        );

        System.out.println("--- First few consumed events ---");
        int shown = 0;
        for (PipelineEvent event : ledger) {
            if (event.eventType() == EventType.CONSUMED) {
                System.out.println(event);
                shown++;
                if (shown == 5) {
                    break;
                }
            }
        }
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