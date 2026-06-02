package org.example.phase4locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    //Similar to ReentrantLock, but the difference here is that this has two different locks for readers and writers,
    //so while Reentrant allowed reader and writer threads to work concurrently at the same time,
    //this only allows multiples of one type, so readers can't work with writers and vice versa at the same time.
    //This is very useful when lots of data needs to be read, but very little of it needs to be updated/changed.
    // if it was 50/50 for read/write this wouldn't make sense because they essentially would block each other.
    private static String data = "Initial Value";

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        //Initial readers
        for (int i = 1; i <= 3; i++) {

            int id = i;

            executor.submit(() -> {

                lock.readLock().lock();

                try {

                    System.out.println("Reader " + id + " reading: " + data);

                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {

                    lock.readLock().unlock();
                }
            });
        }

        //Writer
        executor.submit(() -> {

            lock.writeLock().lock();

            try {

                System.out.println("Writer updating data...");

                data = "Updated Value";


            } finally {

                lock.writeLock().unlock();
            }
        });

        Thread.sleep(4000);

        //Readers after update
        for (int i = 4; i <= 5; i++) {

            int id = i;

            executor.submit(() -> {

                lock.readLock().lock();

                try {

                    System.out.println("Reader " + id + " reads: " + data);

                } finally {

                    lock.readLock().unlock();
                }
            });
        }

        executor.shutdown();
    }
}
