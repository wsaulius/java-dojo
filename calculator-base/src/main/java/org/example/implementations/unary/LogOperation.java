package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntToDoubleFunction;

@Singleton
public class LogOperation implements IntToDoubleFunction {
    @Override
    public double applyAsDouble(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("Log undefined for non-positive values");
        }

        return Math.log(n);
    }
}