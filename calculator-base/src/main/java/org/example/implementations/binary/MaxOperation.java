package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

@Singleton
public class MaxOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return Math.max(left, right);
    }
}