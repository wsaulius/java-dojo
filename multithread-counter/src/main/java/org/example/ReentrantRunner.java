package org.example;


import org.example.implementations.CounterAsObject;
import org.example.implementations.ReentrantCounter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantRunner {

    public static void main(String[] args) throws InterruptedException {

        ReentrantCounter counter = new ReentrantCounter();

        try {

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                        counter.increment("t1");

                    } catch ( Exception exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                        counter.increment("t2");

                    } catch ( Exception exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });

            t1.start(); t2.start();

            t1.join(); t2.join();

        } catch ( Exception exception ) {
            System.err.println( exception );

        };

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