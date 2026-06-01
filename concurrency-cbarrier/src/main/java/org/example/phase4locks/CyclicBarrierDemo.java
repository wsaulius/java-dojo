package org.example.phase4locks;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {
    //Very similar too CountDownLatch, where it waits for all tasks to finish before moving on, no matter the order when they are finished, but
    //the main difference here is that in the CyclicBarrier the workers wait for each other to reach a certain synchronization point/checkpoint,
    //before continuing, and it can be done in phases that's why it's called cyclic.
    // Wait for all workers to reach checkpoint -> Phase 1 checkpoint -> continue -> wait for all workers to reach checkpoint 2 -> Phase 2 checkpoint ->Finish
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CyclicBarrier barrier = new CyclicBarrier(3);

        for (int i = 1; i <= 3; i++) {

            int workerId = i;

            executor.submit(() -> {

                try {

                    System.out.println("Worker " + workerId + " doing phase 1");

                    Thread.sleep((long)(Math.random() * 4000));

                    System.out.println("Worker " + workerId + " reached barrier");

                    barrier.await();

                    System.out.println("Worker " + workerId + " doing phase 2");

                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }

            });
        }
        executor.shutdown();
    }
}
