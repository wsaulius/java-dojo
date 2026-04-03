package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerRunner {

    private static final int LIMIT = 10;
    private static final int TOTAL_ITEMS = 100;

    public static void main(String[] args) throws InterruptedException {

        final AtomicInteger counter = new AtomicInteger(0);

        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(LIMIT);

        final Runnable producer = () -> {
            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {

                    // Put to the BlockingQueue
                    queue.put(i); // blocks if full

                    counter.incrementAndGet();
                    System.out.println("Produced: " + i);
                    randomPause();
                }

                queue.put(-1); // Stop if encountered
                System.out.println(Thread.currentThread().getName() + " producer done");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(producer, "t1");

        Thread t2 = new Thread(() -> {
            try {
                while (true) {
                    int item = queue.take(); // blocks if empty

                    // Stop sign
                    if (item == -1) {
                        break;
                    }

                    System.out.println("Consumed: " + item);
                    randomPause();
                }

                System.out.println(Thread.currentThread().getName() + " consumer done");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Total produced = " + counter.get());
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