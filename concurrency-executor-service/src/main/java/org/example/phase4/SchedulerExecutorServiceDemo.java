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
                    "Start: " + System.currentTimeMillis()
            );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(
                    "End: " + System.currentTimeMillis()
            );
        };

        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS); //Start time -> Start time
        scheduler.scheduleWithFixedDelay(task, 0, 3, TimeUnit.SECONDS); // End time -> Start time

        Thread.sleep(15000);

        scheduler.shutdown();
    }
}
