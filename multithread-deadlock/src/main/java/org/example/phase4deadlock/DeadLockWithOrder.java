package org.example.phase4deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockWithOrder {
    //A simple Deadlock avoidance is to make sure the threads always acquire locks in the same order, that way the threads get locked out
    //when trying to access the same lock as the other thread that already has it locked, and then it has to wait for the lock to release.
    private static final Lock lockA = new ReentrantLock();

    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 2; i++) {

            int id = i;

            executor.submit(() -> {

                lockA.lock();

                try {

                    System.out.println("Task " + id + " got Lock A");

                    sleep();

                    lockB.lock();

                    try {

                        System.out.println("Task " + id + " got Lock B");

                    } finally {
                        lockB.unlock();
                    }

                } finally {
                    lockA.unlock();
                }
            });
        }

        executor.shutdown();
    }

    private static void sleep() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
