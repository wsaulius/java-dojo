package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class FibonacciOperation implements UnaryOperation<Integer, Long> {

    @Override
    public Long apply(Integer n) {

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