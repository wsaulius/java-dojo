package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

/**
 * Unary negation operation.
 */
@Singleton
public final class NegateOperation implements IntUnaryOperator {

    /** {@inheritDoc} */
    @Override
    public int applyAsInt(int n) {
        return -n;
    }
}