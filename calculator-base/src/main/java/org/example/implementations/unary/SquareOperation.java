package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

/**
 * Unary square operation.
 */
@Singleton
public final class SquareOperation implements IntUnaryOperator {

    /** {@inheritDoc} */
    @Override
    public int applyAsInt(int n) {
        return n * n;
    }
}