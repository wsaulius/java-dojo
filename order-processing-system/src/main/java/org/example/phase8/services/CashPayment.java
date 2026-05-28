package org.example.phase8.services;

import org.example.phase8.enums.PaymentType;

//Strategy implementation
public class CashPayment extends AbstractPaymentStrategy {

    @Override
    protected PaymentType getPaymentType() {
        return PaymentType.CASH;
    }

    @Override
    protected int getDelay() {
        return 1500;
    }

    @Override
    protected double getFailureRate() {
        return 0.3;
    }
}
