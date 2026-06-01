package org.example.phase4locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    //Very similar to synchronized() with a key/lock meant to protect data from being corrupted during concurrency.
    //Lock/unlock principle where a thread takes the key so the other thread can't "unlock"
    //and interrupt it , when the thread finished the key is returned and available for taking again.
    //ReentrantLock means the same thread can use the same key multiple times to avoid deadlock, and it has some extra features compared to synchronized().
    //tryLock() - Attempt without locking forever
    //new ReentrantLock(true) - Enables fair locking, which means the threads get the lock in order of who was waiting the longest first
    //like A->B->C instead of B->A->C
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {

            //Thread 1 takes the key
            lock.lock();

            try {
                System.out.println("Thread 1 got the lock");

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                //Always give back the key in case of failure to avoid deadlock
                lock.unlock();
            }
        });

        executor.submit(() -> {

            //Thread 2 tries to get the key and immediatelly gets the answer if it's available or not instead of waiting
            if (lock.tryLock()) {

                try {
                    System.out.println("Thread 2 got the lock");
                } finally {
                    lock.unlock();
                }

            } else {
                System.out.println("Thread 2 could not get lock");
            }
        });

        executor.shutdown();
    }
}
