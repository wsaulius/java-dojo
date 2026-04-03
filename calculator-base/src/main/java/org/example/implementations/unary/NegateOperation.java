package org.example.implementations.unary;

import java.util.function.IntUnaryOperator;

public class NegateOperation implements IntUnaryOperator {

    @Override
    public int applyAsInt(int n) {
        return -n;
    }
}