package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class SqrtOperation implements UnaryOperation<Integer, Double> {
    @Override
    public Double apply(Integer n) {

        if (n < 0) {
            throw new IllegalArgumentException("Square root of negative number");
        }

        return Math.sqrt(n);
    }
}