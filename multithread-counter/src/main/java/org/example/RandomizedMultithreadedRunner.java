package org.example;

import org.example.implementations.CounterAsObject;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedMultithreadedRunner {

    public static void main(String[] args) throws InterruptedException {

        CounterAsObject counter = new CounterAsObject();

        try {

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                        counter.incrementAsSemaphore("t1");

                    } catch ( InterruptedException exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                    counter.incrementAsSemaphore("t2");

                    } catch ( InterruptedException exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });

            Thread t3 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                        counter.incrementAsSemaphore("t3");

                    } catch ( Exception exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });

            Thread t4 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {

                    try {
                        counter.incrementAsSemaphore("t4");

                    } catch ( Exception exception ) {
                        System.err.println( exception );
                    }

                    randomPause();
                }
            });


        t1.start(); t2.start(); t3.start(); t4.start();

        t1.join(); t2.join(); t3.join(); t4.join();

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