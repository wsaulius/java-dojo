package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

/**
 * Unary cube operation.
 */
@Singleton
public final class CubeOperation implements IntUnaryOperator {

    /** {@inheritDoc} */
    @Override
    public int applyAsInt(int n) {
        return n * n * n;
    }
}