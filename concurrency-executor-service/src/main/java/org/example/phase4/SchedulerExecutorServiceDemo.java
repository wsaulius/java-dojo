package org.example.phase4;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            System.out.println(
                    "Task executed at: " +
                            System.currentTimeMillis()
            );
        };

        System.out.println("Scheduling task...");

        scheduler.schedule(task, 3, TimeUnit.SECONDS);

        Thread.sleep(5000); // shutdown might happen before the task executes.

        scheduler.shutdown();
    }
}
