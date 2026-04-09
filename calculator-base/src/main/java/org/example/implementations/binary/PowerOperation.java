package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

@Singleton
public final class PowerOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        return Math.pow(left, right);
    }
}