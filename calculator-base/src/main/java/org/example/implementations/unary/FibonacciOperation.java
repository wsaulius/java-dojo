package org.example.implementations.unary;

import java.util.function.IntToLongFunction;

public class FibonacciOperation implements IntToLongFunction {

    @Override
    public long applyAsLong(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("Negative not allowed");
        }

        if (n == 0) return 0L;
        if (n == 1) return 1L;

        long a = 0;
        long b = 1;

        for (int i = 2; i <= n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }

        return b;
    }
}