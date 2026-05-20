package org.example.phase1.interfaces;

import org.example.phase1.records.Customer;

import java.util.List;

@FunctionalInterface
public interface TotalSumFunctionalInterface {

    double calculate(List<Customer> customers);

    default void printTotalSum(double totalSum){
        System.out.println("Total sum is: " + totalSum);
    }

    static void info() {
        System.out.println("Custom total sum functional interface -");
    }
}
