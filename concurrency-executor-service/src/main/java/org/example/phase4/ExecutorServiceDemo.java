package org.example.phase4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {

    private static final int THREADS = 3;
    private static final int TASKS = 10;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        for (int i = 0; i <= TASKS; i++) {

            int taskId = i;

            executor.submit(() -> {
                System.out.println("Task " + taskId + " running on thread " + Thread.currentThread().getName());

            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Task " + taskId + " finished");
        }
        executor.shutdown();

    }
}
