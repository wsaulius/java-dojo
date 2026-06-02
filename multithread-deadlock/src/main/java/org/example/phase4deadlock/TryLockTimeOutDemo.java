package org.example.phase4deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTimeOutDemo {
    //An extension to tryLock() by adding timeout which waits a certain time for a thread access instead of instantly failing,
    //useful when some operations take some time, but we don't want to wait forever in case something happens, so eventually the threads give up
    private static final Lock lockA = new ReentrantLock();
    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> work("Task 1"));
        executor.submit(() -> work("Task 2"));

        executor.shutdown();
    }

    private static void work(String name) {

        try {
            if (lockA.tryLock(2, TimeUnit.SECONDS)) {

                try {

                    System.out.println(name + " got Lock A");
                    sleep();

                    if (lockB.tryLock(2, TimeUnit.SECONDS)) {

                        try {
                            System.out.println(name + " got Lock B");
                        } finally {
                            lockB.unlock();
                        }

                    } else {
                        System.out.println(name + " timeout waiting for Lock B");
                    }

                } finally {
                    lockA.unlock();
                }
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
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
