package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

/**
 * Binary power operation.
 */
@Singleton
public final class PowerOperation implements DoubleBinaryOperator {

    /** {@inheritDoc} */
    @Override
    public double applyAsDouble(double left, double right) {
        return Math.pow(left, right);
    }
}