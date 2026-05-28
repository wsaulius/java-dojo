package org.example.phase4;

import java.util.Map;
import java.util.concurrent.*;

public class SimpleTaskScheduler {

    private final ScheduledExecutorService scheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();


    public SimpleTaskScheduler(int poolSize) {
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
    }

    public void schedule(String taskId, Runnable task, int delaySeconds) {

        ScheduledFuture<?> future = scheduler.schedule(() -> {

            try {
                System.out.println("Running task: " + taskId + " on " + Thread.currentThread().getName());

                task.run();

            } finally {
                // remove after completion
                tasks.remove(taskId);
            }

        }, delaySeconds, TimeUnit.SECONDS);

        tasks.put(taskId, future);
    }

    public boolean cancel(String taskId) {

        ScheduledFuture<?> future = tasks.get(taskId);

        if (future == null) {
            return false;
        }

        boolean cancelled = future.cancel(false);

        tasks.remove(taskId);

        return cancelled;
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
