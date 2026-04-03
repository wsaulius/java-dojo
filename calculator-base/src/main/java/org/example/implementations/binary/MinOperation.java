package org.example.implementations.binary;

import org.example.interfaces.BinaryOperation;

public class MinOperation implements BinaryOperation<Double> {
    @Override
    public Double apply(Double left, Double right) {
        return Math.min(left, right);
    }
}