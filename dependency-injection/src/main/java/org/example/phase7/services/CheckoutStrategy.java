package org.example.phase7.services;

import org.example.phase7.interfaces.PaymentStrategyInterface;

//Strategy
public class CheckoutStrategy {

    private final PaymentStrategyInterface paymentStrategy;

    public CheckoutStrategy(PaymentStrategyInterface paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}
