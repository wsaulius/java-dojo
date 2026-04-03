package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ConcurrentHashMapRunner {

        private static final int LIMIT = 10;
        private static final int TOTAL_ITEMS = 100;
        private static final int POISON_PILL = -1;

        public static void main(String[] args) throws InterruptedException {

            final AtomicInteger producedCounter = new AtomicInteger(0);
            final AtomicInteger consumedCounter = new AtomicInteger(0);

            final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(LIMIT);
            final ConcurrentHashMap<Integer, String> consumedItems = new ConcurrentHashMap<>();

            final Consumer<Integer> itemConsumer = item -> {
                consumedItems.put(item, item+ ":consumed-by:" + Thread.currentThread().getName());
                consumedCounter.incrementAndGet();

                System.out.println("Consumed: " + item);
            };

            final Runnable producer = () -> {
                try {
                    for (int i = 1; i <= TOTAL_ITEMS; i++) {
                        queue.put(i); // blocks if full

                        producedCounter.incrementAndGet();
                        System.out.println("Produced: " + i);
                        randomPause();
                    }

                    queue.put(POISON_PILL);
                    System.out.println(Thread.currentThread().getName() + " producer done");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            Thread t1 = new Thread(producer, "producer");

            Thread t2 = new Thread(() -> {
                try {
                    while (true) {
                        int item = queue.take(); // blocks if empty

                        if (item == POISON_PILL) {
                            break;
                        }

                        itemConsumer.accept(item);
                        randomPause();
                    }

                    System.out.println(Thread.currentThread().getName() + " consumer done");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "consumer");

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Produced total = " + producedCounter.get());
            System.out.println("Consumed total = " + consumedCounter.get());

            System.out.println("Map size = " + consumedItems.size());

            int randomIndex = randomPause();
            // sample lookup
            System.out.println("Item " + randomIndex + " check: " + consumedItems.get( randomIndex ));
        }

        private static int randomPause() {
            try {
                int millis = ThreadLocalRandom.current().nextInt(1, 100);
                Thread.sleep(millis);
                return millis;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 0;
        }
    }

