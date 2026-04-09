package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntUnaryOperator;

@Singleton
public class CubeOperation implements IntUnaryOperator {

    @Override
    public int applyAsInt(int n) {
        return n * n * n;
    }
}