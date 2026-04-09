package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntToDoubleFunction;

@Singleton
public final class SqrtOperation implements IntToDoubleFunction {

    @Override
    public double applyAsDouble(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("Square root of negative number");
        }

        return Math.sqrt(n);
    }
}