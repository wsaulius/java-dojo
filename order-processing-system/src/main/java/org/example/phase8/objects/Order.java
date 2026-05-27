package org.example.phase8.objects;

public class Order {
    private final int id;
    private final String product;
    private final double amount;
    private final String paymentType;

    public Order(int id, String product, double amount, String paymentType) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public int getId() {
        return id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}
