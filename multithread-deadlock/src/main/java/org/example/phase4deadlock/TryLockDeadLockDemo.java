package org.example.phase4deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDeadLockDemo {
    //An example of deadlock prevention with tryLock, which is basically if it's available - take it, if it's not back off, stop waiting and trigger the else statement.
    private static final Lock lockA = new ReentrantLock();

    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> doWork("Task 1"));
        executor.submit(() -> doWork("Task 2"));
        executor.shutdown();
    }

    private static void doWork(String name) {

        if (lockA.tryLock()) {

            try {

                System.out.println(name + " got Lock A");
                sleep();

                if (lockB.tryLock()) {

                    try {

                        System.out.println(name + " got Lock B");

                    } finally {
                        lockB.unlock();
                    }

                } else {
                    System.out.println(name + " could not get Lock B");
                }

            } finally {
                lockA.unlock();
            }
        }
    }

    private static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
