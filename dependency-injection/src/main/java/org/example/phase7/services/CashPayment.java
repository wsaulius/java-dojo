package org.example.phase7.services;

import org.example.phase7.interfaces.PaymentStrategyInterface;

//Strategy implementation
public class CashPayment implements PaymentStrategyInterface {

    @Override
    public void pay(double amount) {
        System.out.println("Paid with cash: " + amount);
    }
}
