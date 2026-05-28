package org.example.phase8.services;

import org.example.phase8.enums.PaymentType;

//Strategy implementation
public class CardPayment extends AbstractPaymentStrategy {

    @Override
    protected PaymentType getPaymentType() {
        return PaymentType.CARD;
    }

    @Override
    protected int getDelay() {
        return 2000;
    }

    @Override
    protected double getFailureRate() {
        return 0.4;
    }
}
