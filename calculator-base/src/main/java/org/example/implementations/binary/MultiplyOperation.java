package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

/**
 * Binary multiplication operation.
 */
@Singleton
public final class MultiplyOperation implements DoubleBinaryOperator {

    /** {@inheritDoc} */
    @Override
    public double applyAsDouble(double left, double right) {
        return left * right;
    }
}