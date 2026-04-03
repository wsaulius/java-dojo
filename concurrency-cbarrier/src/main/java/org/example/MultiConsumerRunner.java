package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class MultiConsumerRunner {

        private static final int LIMIT = 10;
        private static final int TOTAL_ITEMS = 1000;

        // Keep item index and thread name
        record ConsumedRecord(int item, String threadName) {}

        public static void main(String[] args) throws InterruptedException {

            final Deque<Integer> buffer = new ArrayDeque<>();
            final ConcurrentHashMap<Integer, ConsumedRecord> consumedItems = new ConcurrentHashMap<>();

            final ReentrantLock lock = new ReentrantLock();

            // Introducing Condition (state) locks
            final Condition notEmpty = lock.newCondition();
            final Condition notFull = lock.newCondition();

            final AtomicBoolean producerDone = new AtomicBoolean(false);
            final AtomicInteger consumptionId = new AtomicInteger(0);

            Consumer<Integer> itemConsumer = item -> {

                int id = consumptionId.incrementAndGet();
                ConsumedRecord record = new ConsumedRecord(item, Thread.currentThread().getName());

                consumedItems.put(id, record);

                System.out.println(
                        "Consumed id=" + id +
                                ", item=" + item +
                                ", by=" + record.threadName()
                );
            };

            final Runnable producer = () -> {
                int producedCount = 0;

                try {
                    for (int i = 1; i <= TOTAL_ITEMS; i++) {
                        lock.lock();
                        try {
                            while (buffer.size() == LIMIT) {
                                notFull.await();
                            }

                            buffer.addLast(i);
                            producedCount++;
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

                    // Important!
                    lock.lock();
                    try {
                        producerDone.set(true);
                        notEmpty.signalAll();
                    } finally {
                        lock.unlock();
                    }

                    System.out.println(Thread.currentThread().getName() + " producer done");
                    System.out.println("Produced total = " + producedCount);
                }
            };

            final Runnable consumerTask = () -> {
                try {
                    while (true) {
                        int item;

                        lock.lock();
                        try {
                            while (buffer.isEmpty() && !producerDone.get()) {
                                notEmpty.await();
                            }

                            if (buffer.isEmpty() && producerDone.get()) {
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

            // Several consumers for concurrency, they both write to CHM consumedItems
            Thread consumer1 = new Thread(consumerTask, "consumer-1");
            Thread consumer2 = new Thread(consumerTask, "consumer-2");

            producerThread.start();
            consumer1.start();
            consumer2.start();

            producerThread.join();
            consumer1.join();
            consumer2.join();

            System.out.println("Consumed total = " + consumedItems.size());
            System.out.println("Map size = " + consumedItems.size());

            System.out.println("Sample record 1 -> " + consumedItems.get( randomPause() ));
            System.out.println("Sample record 2 -> " + consumedItems.get( randomPause() ));
            System.out.println("Sample record 3 -> " + consumedItems.get( randomPause() ));
        }

        private static int randomPause() {
            try {
                int millis = ThreadLocalRandom.current().nextInt(1, 10);
                Thread.sleep(millis);
                return millis;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return 0;
        }
    }