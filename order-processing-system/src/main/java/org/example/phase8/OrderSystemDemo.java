package org.example.phase8;

import org.example.phase8.consumers.OrderConsumer;
import org.example.phase8.decorators.LoggingDecorator;
import org.example.phase8.interfaces.OrderProcessor;
import org.example.phase8.objects.Order;
import org.example.phase8.observers.AnalyticsListener;
import org.example.phase8.observers.EmailListener;
import org.example.phase8.observers.SmsListener;
import org.example.phase8.producers.OrderProducer;
import org.example.phase8.publishers.OrderEventManager;
import org.example.phase8.services.BasicOrderProcessor;
import org.example.phase8.services.PaymentFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderSystemDemo {
    public static void main(String[] args) {

        BlockingQueue<Order> queue =
                new LinkedBlockingQueue<>();

        PaymentFactory factory = new PaymentFactory();
        OrderEventManager manager = new OrderEventManager();

        manager.order(new EmailListener());
        manager.order(new SmsListener());
        manager.order(new AnalyticsListener());

        OrderProcessor processor = new LoggingDecorator(
                new BasicOrderProcessor(factory, manager)
                );

        ExecutorService mainExecutor =
                Executors.newFixedThreadPool(3);

        ExecutorService processingExecutor =
                Executors.newFixedThreadPool(3);

        mainExecutor.submit(new OrderProducer(queue));

        mainExecutor.submit(new OrderConsumer(queue, processor, processingExecutor));

        mainExecutor.submit(new OrderConsumer(queue, processor, processingExecutor));

    }
}
