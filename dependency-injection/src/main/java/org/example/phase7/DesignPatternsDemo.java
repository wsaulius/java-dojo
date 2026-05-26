package org.example.phase7;

import org.example.phase7.interfaces.PaymentStrategyInterface;
import org.example.phase7.services.CardPayment;
import org.example.phase7.services.CashPayment;
import org.example.phase7.services.CheckoutStrategy;
import org.example.phase7.services.CryptoPayment;

public class DesignPatternsDemo {
    public static void main(String[] args) {
        //Runtime selection
        PaymentStrategyInterface cardPayment = new CardPayment();
        PaymentStrategyInterface cashPayment = new CashPayment();
        PaymentStrategyInterface cryptoPayment = new CryptoPayment();
        CheckoutStrategy checkoutServiceCard = new CheckoutStrategy(cardPayment);
        CheckoutStrategy checkoutServiceCash = new CheckoutStrategy(cashPayment);
        CheckoutStrategy checkoutServiceCrypto = new CheckoutStrategy(cryptoPayment);
        checkoutServiceCard.checkout(200);
        checkoutServiceCash.checkout(100);
        checkoutServiceCrypto.checkout(50);
    }
}
