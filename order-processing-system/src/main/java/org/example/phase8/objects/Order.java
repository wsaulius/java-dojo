package org.example.phase8.objects;

import org.example.phase8.enums.PaymentType;

public class Order {
    private final int id;
    private final String product;
    private final double amount;
    private final PaymentType paymentType;

    public Order(int id, String product, double amount, PaymentType paymentType) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public int getId() {
        return id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public String toString() {
        return "Order id=" + id + ", product=" + product + ", amount=" + amount + ", paymentType=" + paymentType;
    }
}
