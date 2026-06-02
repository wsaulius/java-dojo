package org.example.phase4deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    //Simple definition of a deadlock is when two or more threads are waiting for resources held by each other, and nobody can proceed.
    //As can be seen by this example each thread locks a separate lock each, but then want each other's locks after, but that cannot happen since they are both locked,
    //which makes the program hang forever.

    private static final Lock lockA = new ReentrantLock();

    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {

            System.out.println("Task 1 wants Lock A");

            lockA.lock();

            try {

                System.out.println("Task 1 got Lock A");

                sleep();

                System.out.println("Task 1 wants Lock B");

                lockB.lock();

                try {

                    System.out.println("Task 1 got Lock B");

                } finally {
                    lockB.unlock();
                }

            } finally {
                lockA.unlock();
            }
        });

        executor.submit(() -> {

            System.out.println("Task 2 wants Lock B");

            lockB.lock();

            try {

                System.out.println("Task 2 got Lock B");

                sleep();

                System.out.println("Task 2 wants Lock A");

                lockA.lock();

                try {

                    System.out.println("Task 2 got Lock A");

                } finally {
                    lockA.unlock();
                }

            } finally {
                lockB.unlock();
            }
        });

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
