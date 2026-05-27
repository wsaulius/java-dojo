package org.example.phase8.decorators;

import org.example.phase8.interfaces.OrderProcessor;
import org.example.phase8.objects.Order;

//Decorator
public class LoggingDecorator implements OrderProcessor {

    private final OrderProcessor wrapped;

    public LoggingDecorator(OrderProcessor wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void process(Order order) {
        System.out.println("[LOG] Starting " + order);

        wrapped.process(order);

        System.out.println("[LOG] Finished " + order);
    }
}
