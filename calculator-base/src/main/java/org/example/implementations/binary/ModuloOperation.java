package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

@Singleton
public final class ModuloOperation implements DoubleBinaryOperator {

    @Override
    public double applyAsDouble(double left, double right) {
        if (right == 0) {
            throw new ArithmeticException("Modulo by zero");
        }

        return left % right;
    }
}