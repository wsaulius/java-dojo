package org.example;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierRunner {

    public static void main(String[] args) throws InterruptedException {

        final AtomicInteger counter = new AtomicInteger(0);

        /* Let's wait until both threads finish, sync the time and finish them both at the same time.
         Use a barrier only if both worker threads must stop at the same point and continue only
         when both have arrived.
        */
        final CyclicBarrier barrier = new CyclicBarrier(2);

        final Runnable asRunnable = () -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    counter.incrementAndGet();
                    System.out.print(".");
                }

                barrier.await();
                System.out.println(Thread.currentThread().getName() + " done");

            } catch (Exception e) {
                System.err.println(e);
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(asRunnable, "t1");

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    counter.incrementAndGet();
                    randomPause();
                }

                barrier.await();
                System.out.println(Thread.currentThread().getName() + " done");

            } catch (Exception e) {
                System.err.println(e);
                Thread.currentThread().interrupt();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter = " + counter.get());
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