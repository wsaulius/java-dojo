package org.example;

import org.example.implementations.CounterAsObject;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedMultithreadedRunner {

    public static void main(String[] args) throws InterruptedException {

        CounterAsObject counter = new CounterAsObject();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment("t1");
                randomPause();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment("t2");
                randomPause();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println( counter.counter() );
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