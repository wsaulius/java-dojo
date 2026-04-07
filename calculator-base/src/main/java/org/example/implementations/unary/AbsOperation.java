package org.example.implementations.unary;

import java.util.function.IntUnaryOperator;

public class AbsOperation implements IntUnaryOperator {

    @Override
    public int applyAsInt(int n) {
        return Math.abs(n);
    }
}