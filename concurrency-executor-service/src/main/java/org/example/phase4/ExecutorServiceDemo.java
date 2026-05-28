package org.example.phase4;

import java.util.concurrent.*;

public class ExecutorServiceDemo {

    private static final int THREADS = 3;
    private static final int TASKS = 10;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        Callable<Integer> task = () -> {
            System.out.println("Callable... ");
            Thread.sleep(3000);
            return 30;
        };

        Future<Integer> future = executor.submit(task);

        System.out.println("Task submitted.");

        try {
            System.out.println("Doing other work...");

            Integer result = future.get();

            System.out.println("Result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
       /* Main Thread:
        submit task ---- continue working ---- waits on get()

        Worker Thread:
        start task ---- sleep ---- return 30 */

        executor.shutdown();

    }
}
