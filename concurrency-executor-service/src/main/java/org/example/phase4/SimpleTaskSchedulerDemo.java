package org.example.phase4;

import java.util.concurrent.ScheduledFuture;

public class SimpleTaskSchedulerDemo {

    public static void main(String[] args)
            throws InterruptedException {

        SimpleTaskScheduler scheduler =
                new SimpleTaskScheduler(2);

        scheduler.schedule("task1", () -> {
            System.out.println("Task 1 executed");
        }, 5);

        scheduler.schedule("task2", () -> {
            System.out.println("Task 2 executed");
        }, 10);

        System.out.println("Tasks scheduled");

        Thread.sleep(2000);

        boolean cancelled =
                scheduler.cancel("task2");

        System.out.println("Task2 cancelled: " + cancelled);

        Thread.sleep(12000);

        scheduler.shutdown();
    }
}
