package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntToDoubleFunction;

/**
 * Unary exponential operation.
 */
@Singleton
public final class ExpOperation implements IntToDoubleFunction {

    /** {@inheritDoc} */
    @Override
    public double applyAsDouble(int n) {
        return Math.exp(n);
    }
}