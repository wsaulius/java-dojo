package org.example.implementations.binary;

import org.example.interfaces.BinaryOperation;

public class MaxOperation implements BinaryOperation<Double> {
    @Override
    public Double apply(Double left, Double right) {
        return Math.max(left, right);
    }
}