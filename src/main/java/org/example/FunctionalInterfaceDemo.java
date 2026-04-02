package org.example;

import org.example.implementations.PercentageCalculator;
import org.example.interfaces.MyFunctionalInterface;

public class FunctionalInterfaceDemo {

    public static void main(String[] args) {

        // Using implementation class
        MyFunctionalInterface percentageCalc = new PercentageCalculator();
        double result = percentageCalc.operate(200, 15); // 15% of 200

        percentageCalc.printResult(result);
        percentageCalc.printAsPercentage(15);

        // Using lambda for comparison
        MyFunctionalInterface addition = (a, b) -> a + b;
        double sum = addition.operate(10, 5);
        addition.printResult(sum);

        MyFunctionalInterface.info();
    }
}