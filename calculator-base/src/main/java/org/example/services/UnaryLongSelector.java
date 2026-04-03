package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.UnaryLongType;
import org.example.interfaces.UnaryOperation;

import java.util.Map;

public class UnaryLongSelector {

    private final Map<UnaryLongType, UnaryOperation<Integer, Long>> operations;

    @Inject
    public UnaryLongSelector(Map<UnaryLongType, UnaryOperation<Integer, Long>> operations) {
        this.operations = operations;
    }

    public UnaryOperation<Integer, Long> get(UnaryLongType type) {
        UnaryOperation<Integer, Long> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary long operation registered for: " + type);
        }
        return operation;
    }
}