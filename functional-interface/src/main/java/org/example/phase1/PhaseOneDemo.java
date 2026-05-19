package org.example.phase1;

import org.example.phase1.records.Customer;
import org.example.phase1.records.Order;

import java.util.List;

public class PhaseOneDemo {
    public static void main(String[] args) {
//Base setup
        //Orders for Customer1
        Order order1 = new Order(1, 100.00);
        Order order2 = new Order(2, 150.00);

        //Orders for Customer2
        Order order3 = new Order(3, 200.00);
        Order order4 = new Order(4, 300.00);

        Customer customer1 = new Customer(1, List.of(order1, order2));
        Customer customer2 = new Customer(1, List.of(order3, order4));

        List<Customer> customerList = List.of(customer1, customer2);

        double totalSum = 0;

        for(Customer c: customerList) {
            for(Order o: c.orders()) {
                totalSum += o.amount();
            }
        }

        System.out.println(totalSum);
    }
}
