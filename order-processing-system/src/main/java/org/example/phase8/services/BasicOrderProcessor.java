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

        boolean success = strategy.pay(order);

        if (success) {
            orderEventManager.notifyOnOrderComplete(order);
        }

    }
}
