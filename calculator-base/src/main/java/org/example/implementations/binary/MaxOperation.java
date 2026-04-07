package org.example.implementations.binary;

import java.util.function.DoubleBinaryOperator;

public class MaxOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return Math.max(left, right);
    }
}