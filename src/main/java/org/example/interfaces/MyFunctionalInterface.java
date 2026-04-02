package org.example.interfaces;

@FunctionalInterface
public interface MyFunctionalInterface {

    // Abstract method
    double operate(double a, double b);

    // Default method
    default void printResult(double result) {
        System.out.println("Result is: " + result);
    }

    // Default helper method for percentage formatting
    default void printAsPercentage(double value) {
        System.out.println("Percentage: " + value + "%");
    }

    static void info() {
        System.out.println("Functional Interface with default methods");
    }
}
