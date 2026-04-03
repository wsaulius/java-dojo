package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.UnaryIntType;
import org.example.interfaces.UnaryOperation;

import java.util.Map;

public class UnaryIntSelector {

    private final Map<UnaryIntType, UnaryOperation<Integer, Integer>> operations;

    @Inject
    public UnaryIntSelector(Map<UnaryIntType, UnaryOperation<Integer, Integer>> operations) {
        this.operations = operations;
    }

    public UnaryOperation<Integer, Integer> get(UnaryIntType type) {
        UnaryOperation<Integer, Integer> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary int operation registered for: " + type);
        }
        return operation;
    }
}