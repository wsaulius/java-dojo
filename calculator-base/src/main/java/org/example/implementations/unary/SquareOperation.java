package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

@Singleton
public class SquareOperation implements IntUnaryOperator {

    @Override
    public int applyAsInt(int n) {
        return n * n;
    }
}