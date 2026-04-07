package org.example.implementations.binary;

import java.util.function.DoubleBinaryOperator;

public class DivideOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        if (right == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left / right;
    }
}