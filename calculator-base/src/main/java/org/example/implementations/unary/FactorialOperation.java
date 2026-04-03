package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class FactorialOperation implements UnaryOperation<Integer, Long> {
    @Override
    public Long apply(Integer n) {
        if (n < 0) throw new IllegalArgumentException("Negative not allowed");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}