package org.example.phase8.services;

import org.example.phase8.enums.PaymentType;
import org.example.phase8.interfaces.PaymentStrategyInterface;
import org.example.phase8.objects.Order;

public abstract class AbstractPaymentStrategy implements PaymentStrategyInterface {

    protected abstract PaymentType getPaymentType();

    protected abstract int getDelay();

    protected abstract double getFailureRate();

    @Override
    public void pay(Order order) {
        System.out.println("Processing " + getPaymentType() + " payment for " + order);

        simulateDelay();
        simulateFailure();

        System.out.println(getPaymentType() + " payment SUCCESS for " + order);
    }

    private void simulateDelay() {

        try {
            Thread.sleep(getDelay());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulateFailure() {

        if (Math.random() < getFailureRate()) {
            throw new RuntimeException(getPaymentType() + " payment FAILED");
        }
    }
}
