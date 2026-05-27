package org.example.phase8.services;

import org.example.phase8.interfaces.PaymentStrategyInterface;

import java.util.Map;

//Strategy
public class PaymentFactory {

    private final Map<String, PaymentStrategyInterface> strategies;

    public PaymentFactory() {
        strategies = Map.of(
                "CARD", new CardPayment(),
                "CASH", new CashPayment(),
                "CRYPTO", new CryptoPayment()
        );
    }

    public PaymentStrategyInterface getStrategy(String paymentType) {
        return strategies.get(paymentType);
    }
}
