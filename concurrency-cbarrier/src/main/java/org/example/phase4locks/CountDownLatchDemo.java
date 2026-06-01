package org.example.phase4locks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    //Tasks run parallel but ALL tasks have to finish before the process can continue.
    //Example:
    //Task 1 -> Load users
    //Task 2 -> Load products
    //Task 3 -> Load orders
    //These all must finish to continue and complete a final task - To generate a report
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        //Counter
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 1; i <= 3; i++) {
            int id = i;

            executor.submit(() -> {
                try {
                    System.out.println("Task " + id + " started");

                    //Stimulating task
                    Thread.sleep((long) (Math.random() * 4000));

                    System.out.println("Task " + id + " finished");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    //Decrementing counter
                    latch.countDown();
                    System.out.println("Latch count: " + latch.getCount());
                }
            });
        }
        System.out.println("Main thread waiting...");

        //Main thread waits for all task to finish
        latch.await();

        System.out.println("All tasks completed!");

        executor.shutdown();
    }
}
