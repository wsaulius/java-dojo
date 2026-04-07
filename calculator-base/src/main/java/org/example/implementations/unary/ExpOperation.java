package org.example.implementations.unary;

import java.util.function.IntToDoubleFunction;

public class ExpOperation implements IntToDoubleFunction {

    @Override
    public double applyAsDouble(int n) {
        return Math.exp(n);
    }
}