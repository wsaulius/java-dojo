package org.example.implementations.binary;

import java.util.function.DoubleBinaryOperator;

public class SubtractOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return left - right;
    }
}