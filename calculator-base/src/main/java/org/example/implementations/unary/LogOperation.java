package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class LogOperation implements UnaryOperation<Integer, Double> {
    @Override
    public Double apply(Integer n) {

        if (n <= 0) {
            throw new IllegalArgumentException("Log undefined for non-positive values");
        }

        return Math.log(n);
    }
}