package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

/**
 * Unary absolute value operation.
 */
@Singleton
public final class AbsOperation implements IntUnaryOperator {

    /** {@inheritDoc} */
    @Override
    public int applyAsInt(int n) {
        return Math.abs(n);
    }
}