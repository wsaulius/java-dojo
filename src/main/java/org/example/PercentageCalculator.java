package org.example;

// Implementation class (NOT using lambda)
class PercentageCalculator implements MyFunctionalInterface {

    // Calculate percentage: (value * percentage) / 100
    @Override
    public double operate(double value, double percentage) {
        return (value * percentage) / 100.0;
    }

    // Optionally override default method
    @Override
    public void printResult(double result) {
        System.out.println("Calculated percentage value: " + result);
    }
}