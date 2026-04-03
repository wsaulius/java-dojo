package org.example.implementations.unary;

import java.util.function.IntToDoubleFunction;

public class SqrtOperation implements IntToDoubleFunction {

    @Override
    public double applyAsDouble(int n) {

        if (n < 0) {
            throw new IllegalArgumentException("Square root of negative number");
        }

        return Math.sqrt(n);
    }
}