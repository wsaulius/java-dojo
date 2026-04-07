package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.UnaryIntType;

import java.util.Map;
import java.util.function.IntUnaryOperator;

public class UnaryIntSelector {

    private final Map<UnaryIntType, IntUnaryOperator> operations;

    @Inject
    public UnaryIntSelector(Map<UnaryIntType, IntUnaryOperator> operations) {
        this.operations = operations;
    }

    public IntUnaryOperator get(UnaryIntType type) {
        IntUnaryOperator operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary int operation registered for: " + type);
        }
        return operation;
    }
}