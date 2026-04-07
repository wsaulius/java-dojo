package org.example.implementations.binary;

import java.util.function.DoubleBinaryOperator;

public class MinOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return Math.min(left, right);
    }

}