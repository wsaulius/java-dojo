package org.example.phase7;

import org.example.phase7.interfaces.EventListener;
import org.example.phase7.interfaces.PaymentStrategyInterface;
import org.example.phase7.observers.EmailListener;
import org.example.phase7.observers.SmsListener;
import org.example.phase7.publishers.EventManager;
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

        //Observer/Publisher usage
        EventManager manager = new EventManager();
        manager.order(new EmailListener());
        manager.order(new SmsListener());
        manager.notifyListeners("Order complete");
    }
}
