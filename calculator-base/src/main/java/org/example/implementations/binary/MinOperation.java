package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

/**
 * Binary minimum operation.
 */
@Singleton
public final class MinOperation implements DoubleBinaryOperator {

    /** {@inheritDoc} */
    @Override
    public double applyAsDouble(double left, double right) {
        return Math.min(left, right);
    }

}