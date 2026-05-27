package org.example.phase8.services;

import org.example.phase8.enums.PaymentType;
import org.example.phase8.interfaces.PaymentStrategyInterface;

import java.util.Map;

//Strategy
public class PaymentFactory {

    private final Map<PaymentType, PaymentStrategyInterface> strategies;

    public PaymentFactory() {
        strategies = Map.of(
                PaymentType.CARD, new CardPayment(),
                PaymentType.CASH, new CashPayment(),
                PaymentType.CRYPTO, new CryptoPayment()
        );
    }

    public PaymentStrategyInterface getStrategy(PaymentType paymentType) {
        return strategies.get(paymentType);
    }
}
