package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntToDoubleFunction;

@Singleton
public final class ExpOperation implements IntToDoubleFunction {

    @Override
    public double applyAsDouble(int n) {
        return Math.exp(n);
    }
}