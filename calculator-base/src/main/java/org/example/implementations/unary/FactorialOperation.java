package org.example.implementations.unary;

import java.util.function.IntToLongFunction;

public class FactorialOperation implements IntToLongFunction {

    @Override
    public long applyAsLong(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative not allowed");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}