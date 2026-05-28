package org.example.phase8.consumers;

import org.example.phase8.interfaces.OrderProcessor;
import org.example.phase8.objects.Order;

import java.util.concurrent.*;

public class OrderConsumer implements Runnable {

    private final BlockingQueue<Order> queue;
    private final OrderProcessor processor;
    private final ExecutorService processingExecutor;

    public OrderConsumer(BlockingQueue<Order> queue, OrderProcessor processor, ExecutorService processingExecutor) {
        this.queue = queue;
        this.processor = processor;
        this.processingExecutor = processingExecutor;
    }

    @Override
    public void run() {

        while (true) {

            try {

                Order order = queue.take();

                CompletableFuture.runAsync(() -> {
                            processor.process(order);
                        }, processingExecutor)
                        .orTimeout(3, TimeUnit.SECONDS)
                        .exceptionally(ex -> {

                            System.out.println("Async error for " + order + ": " + ex.getMessage());
                            fallback(order);

                            return null;
                        });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    private void fallback(Order order) {
        System.out.println("[FALLBACK] Saving failed order: " + order);
    }
}
