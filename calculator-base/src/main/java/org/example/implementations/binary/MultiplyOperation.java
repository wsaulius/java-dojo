package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

@Singleton
public class MultiplyOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return left * right;
    }
}