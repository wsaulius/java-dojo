package org.example.phase1;

import org.example.phase1.collectors.TotalSumCollector;
import org.example.phase1.interfaces.TotalSumFunctionalInterface;
import org.example.phase1.records.Customer;
import org.example.phase1.records.Order;

import java.util.List;

public class PhaseOneDemo {
    public static void main(String[] args) {

        //Base setup
        double totalSum = 0;

        //Orders for Customer1
        Order order1 = new Order(1, 100.00);
        Order order2 = new Order(2, 150.00);

        //Orders for Customer2
        Order order3 = new Order(3, 200.00);
        Order order4 = new Order(4, 300.00);

        //Example: Convert a list of orders into total revenue using streams.
        List<Order> orderList = List.of(order1, order2, order3, order4);

        for(Order o: orderList) {
            totalSum += o.amount();
        }

        System.out.println("Orders total sum using for loop: " + totalSum);

        totalSum = orderList.stream()
                .mapToDouble(Order::amount)
                .sum();

        System.out.println("Orders total sum converting for loop using streams: " + totalSum);
        totalSum = 0;

        //Exercise: Refactor nested loops into stream pipelines.
        Customer customer1 = new Customer(1, List.of(order1, order2));
        Customer customer2 = new Customer(1, List.of(order3, order4));

        List<Customer> customerList = List.of(customer1, customer2);

        for(Customer c: customerList) {
            for(Order o: c.orders()) {
                totalSum += o.amount();
            }
        }

        System.out.println("Orders total using nested loops: " + totalSum);

        totalSum = customerList.stream()
                .flatMap(customer -> customer.orders().stream())
                .mapToDouble(order -> order.amount())
                .sum();

        System.out.println("Orders total converting nested loops using streams: " + totalSum);

        //Using a custom TotalSumFunctionalInterface
        TotalSumFunctionalInterface totalSumCalculator = customers ->
                customers.stream()
                        .flatMap(customer -> customer.orders().stream())
                        .mapToDouble(order -> order.amount())
                        .sum();

        totalSum = totalSumCalculator.calculate(customerList);
        TotalSumFunctionalInterface.info();
        totalSumCalculator.printTotalSum(totalSum);

        //Exercise: Implement a custom collector
        totalSum = customerList.stream()
                .flatMap(customer -> customer.orders().stream())
                .collect(new TotalSumCollector());

        System.out.println("Orders total using custom collector: " + totalSum);

        //Using a custom functional interface + custom collector
        totalSumCalculator = customers ->
                customers.stream()
                        .flatMap(customer -> customer.orders().stream())
                        .collect(new TotalSumCollector());

        totalSum = totalSumCalculator.calculate(customerList);
        System.out.println("Orders total using custom functional interface and custom collector: " + totalSum);

    }


}
