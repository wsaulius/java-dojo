package org.example.phase8.services;

import org.example.phase8.interfaces.OrderProcessor;
import org.example.phase8.interfaces.PaymentStrategyInterface;
import org.example.phase8.objects.Order;
import org.example.phase8.publishers.OrderEventManager;

//Common Decorator interface implementation
public class BasicOrderProcessor implements OrderProcessor {

    private final PaymentFactory factory;
    private final OrderEventManager orderEventManager;

    public BasicOrderProcessor(PaymentFactory factory, OrderEventManager orderEventManager) {
        this.factory = factory;
        this.orderEventManager = orderEventManager;
    }

    @Override
    public void process(Order order) {
        PaymentStrategyInterface strategy = factory.getStrategy(order.getPaymentType());

        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {

            try {

                System.out.println("[PROCESSOR] Attempt " + attempt + " for " + order);
                strategy.pay(order);
                System.out.println("[PROCESSOR] SUCCESS " + order);
                orderEventManager.notifyOnOrderComplete(order);

                return;

            } catch (Exception e) {

                System.out.println("[PROCESSOR] FAILED attempt " + attempt + " for " + order + " -> " + e.getMessage());
                sleep(1000);
            }
        }
        handleFailure(order);
    }


    private void handleFailure(Order order) {

        System.out.println(
                "[PROCESSOR] ORDER FAILED permanently: "
                        + order
        );
    }

    private void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
