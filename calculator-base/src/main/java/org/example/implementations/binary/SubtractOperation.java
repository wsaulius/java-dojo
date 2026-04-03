package org.example.implementations.binary;

import org.example.interfaces.BinaryOperation;

public class SubtractOperation implements BinaryOperation<Double> {
    @Override
    public Double apply(Double left, Double right) {
        return left - right;
    }
}