package org.example.phase4locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        /*Virtual threads are a newer implementation to concurrency.
        "Old" java threads are also called Platform or OS threads since each Java thread is backed by operating system thread.
        That means each thread is expensive since it requires individual memory, stack, scheduling, context switching and etc...
        But most of the time a lot of threads are not working, they are just waiting, so lots of resources just get wasted constantly.
        That's where the virtual threads shine since it's a cheaper alternative, and it's perfect for jobs where there is a lot of waiting involved,
        since java schedules the threads directly and not the OS itself.
        All the concurrency, locks, synchronization and everything stays and works the same.
        Platform threads still have their use on CPU heavy tasks since the CPU is already busy and allocated to the thread itself,
        and virtual threads don't make the CPU faster.
        So essentially irtual Threads are not about making computation faster.
        They're about making blocking/waiting tasks scale massively without needing thousands of expensive OS threads.*/

            long start = System.currentTimeMillis();

            //Comment/uncomment for platform/virtual threads
            //ExecutorService executor = Executors.newFixedThreadPool(1000);
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            for (int i = 0; i < 10000; i++) {

                executor.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);

            long end = System.currentTimeMillis();

            //Comment/uncomment for platform/virtual threads
            //Expected about 10s since we have 1000 threads for 10000 operations
            //System.out.println("Platform Threads: " + (end - start) + " ms");

            //Expected about 1s since all threads can wait at once
            System.out.println("Virtual Threads: " + (end - start) + " ms");
        }
}
