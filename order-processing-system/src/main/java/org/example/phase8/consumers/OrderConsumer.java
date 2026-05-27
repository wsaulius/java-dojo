package org.example.phase8.consumers;

import org.example.phase8.interfaces.OrderProcessor;
import org.example.phase8.objects.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                }, processingExecutor);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }
}
