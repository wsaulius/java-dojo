package org.example.phase4deadlock;

import java.util.Map;
import java.util.concurrent.*;

public class WatchDogDeadlock {
    //Watchdog monitors the threads/workers work and if something looks stuck it can be programmed to perform some tasks like inform/alert/retry/cancel/log
    private static final Map<String, Long> heartbeats = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        ExecutorService workers = Executors.newFixedThreadPool(3);
        ScheduledExecutorService watchdog = Executors.newSingleThreadScheduledExecutor();

        workers.submit(() -> worker("Worker-1", false));
        workers.submit(() -> worker("Worker-2", false));
        workers.submit(() -> worker("Worker-3", true));

        watchdog.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();

            heartbeats.forEach((name, lastSeen) -> {
                long secondsSinceHeartbeat = (now - lastSeen) / 1000;

                if (secondsSinceHeartbeat > 5) {
                    System.out.println("WATCHDOG WARNING: " + name + " appears stuck");
                }
            });
        }, 0, 2, TimeUnit.SECONDS);
    }

    private static void worker(String name, boolean simulateFailure) {

        try {
            int counter = 0;

            while (true) {

                if (simulateFailure && counter == 3) {
                    System.out.println(name + " stopped sending heartbeats");
                    return;
                }
                heartbeats.put(name, System.currentTimeMillis());
                System.out.println(name + " heartbeat");
                counter++;

                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
