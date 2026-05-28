package org.example.phase8.services;

import org.example.phase8.enums.PaymentType;

//Strategy implementation
public class CryptoPayment extends AbstractPaymentStrategy {

    @Override
    protected PaymentType getPaymentType() {
        return PaymentType.CRYPTO;
    }

    @Override
    protected int getDelay() {
        return 1700;
    }

    @Override
    protected double getFailureRate() {
        return 0.2;
    }
}
