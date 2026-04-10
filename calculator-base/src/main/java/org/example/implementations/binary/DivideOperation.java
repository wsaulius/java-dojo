package org.example.implementations.binary;

import jakarta.inject.Singleton;

import java.util.function.DoubleBinaryOperator;

/**
 * Binary division operation.
 */
@Singleton
public final class DivideOperation implements DoubleBinaryOperator {

    /** {@inheritDoc} */
    @Override
    public double applyAsDouble(double left, double right) {
        if (right == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left / right;
    }
}