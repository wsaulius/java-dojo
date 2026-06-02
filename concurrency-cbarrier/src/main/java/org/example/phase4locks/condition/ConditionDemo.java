package org.example.phase4locks.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConditionDemo {
    //This is basically a newer version of wait(), notify() with synchronized() and now instead we use await(), signal() with ReentrantLock.
    //As it's name suggest this is a Condition AFTER the lock has been acquired, but it cannot continue further,
    //so the basic principle is "I have the lock and entered already, but I cannot continue so what should I do?" or what condition should I fulfill while waiting?
    //signal() - Awake a single waiting thread/consumer
    //signalAll() - Awake all waiting threads/consumers
    //await() - Thread sleeps, but also RELEASES THE LOCK
    public static void main(String[] args) {

        MessageQueue queue = new MessageQueue();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {

            try {
                String message = queue.consume();

                System.out.println("Consumed: " + message);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.submit(() -> {

            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            queue.produce("Produced message.");
        });

        executor.shutdown();
    }
}
