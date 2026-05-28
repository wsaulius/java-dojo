package org.example.phase8.interfaces;

import org.example.phase8.objects.Order;

//Common interface
public interface PaymentStrategyInterface {
    void pay(Order order);
}
