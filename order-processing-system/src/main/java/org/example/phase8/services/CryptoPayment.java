package org.example.phase8.services;

import org.example.phase8.interfaces.PaymentStrategyInterface;
import org.example.phase8.objects.Order;

//Strategy implementation
public class CryptoPayment implements PaymentStrategyInterface {

    @Override
    public boolean pay(Order order) {
        System.out.println("Crypto payment processed for order : " + order);
        return true;
    }
}
