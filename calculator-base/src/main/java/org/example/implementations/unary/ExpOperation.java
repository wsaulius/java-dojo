package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class ExpOperation implements UnaryOperation<Integer, Double> {
    @Override
    public Double apply(Integer n) {
        return Math.exp(n);
    }
}