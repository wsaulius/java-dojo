package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

@Singleton
public final class AbsOperation implements IntUnaryOperator {

    @Override
    public int applyAsInt(int n) {
        return Math.abs(n);
    }
}