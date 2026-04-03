package org.example.implementations.binary;

import org.example.interfaces.BinaryOperation;

public class PowerOperation implements BinaryOperation<Double> {
    @Override
    public Double apply(Double left, Double right) {
        return Math.pow(left, right);
    }
}