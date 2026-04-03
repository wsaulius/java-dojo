package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.UnaryBooleanType;
import org.example.interfaces.UnaryOperation;

import java.util.Map;

public class UnaryBooleanSelector {

    private final Map<UnaryBooleanType, UnaryOperation<Integer, Boolean>> operations;

    @Inject
    public UnaryBooleanSelector(Map<UnaryBooleanType, UnaryOperation<Integer, Boolean>> operations) {
        this.operations = operations;
    }

    public UnaryOperation<Integer, Boolean> get(UnaryBooleanType type) {
        UnaryOperation<Integer, Boolean> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary boolean operation registered for: " + type);
        }
        return operation;
    }
}