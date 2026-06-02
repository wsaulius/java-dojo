package org.example.phase4deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyDemo {
    //Allows the thread to wait for the lock, but it can be interrupted/canceled manually at any moment
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Task 1 grabs the lock and holds it
        executor.submit(() -> {
            lock.lock();
            try {
                System.out.println("Task 1 got the lock");
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } finally {
                lock.unlock();
                System.out.println("Task 1 released lock");
            }
        });

        Thread.sleep(500);

        // Task 2 tries to get the lock
        Future<?> future = executor.submit(() -> {

            try {
                System.out.println("Task 2 trying to get lock...");
                lock.lockInterruptibly();

                try {
                    System.out.println("Task 2 got lock");

                } finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                System.out.println("Task 2 interrupted while waiting");
            }
        });

        Thread.sleep(3000);
        System.out.println("Cancelling Task 2...");
        future.cancel(true);

        executor.shutdown();
    }
}
