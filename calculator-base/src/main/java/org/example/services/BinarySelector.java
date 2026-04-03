package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.BinaryType;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;

// BinarySelector

public class BinarySelector {

    private final Map<BinaryType, DoubleBinaryOperator> operations;

    @Inject
    public BinarySelector(Map<BinaryType, DoubleBinaryOperator> operations) {
        this.operations = operations;
    }

    public DoubleBinaryOperator get(BinaryType type) {
        DoubleBinaryOperator operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No binary operation registered for: " + type);
        }
        return operation;
    }
}