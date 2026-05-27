package org.example.phase8.producers;

import org.example.phase8.enums.PaymentType;
import org.example.phase8.objects.Order;

import java.util.concurrent.BlockingQueue;

public class OrderProducer implements Runnable{
    private final BlockingQueue<Order> queue;

    PaymentType[] paymentTypes = PaymentType.values();

    public OrderProducer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {

                Order order = new Order(i, "Laptop", 1000,
                        paymentTypes[i % paymentTypes.length]
                );

                queue.put(order);
                System.out.println("Produced: " + order);
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
