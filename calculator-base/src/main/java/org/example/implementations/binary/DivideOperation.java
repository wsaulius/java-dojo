package org.example.implementations.binary;

import org.example.interfaces.BinaryOperation;

public class DivideOperation implements BinaryOperation<Double> {
    @Override
    public Double apply(Double left, Double right) {
        if (right == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left / right;
    }
}