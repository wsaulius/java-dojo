package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.BinaryOperation;

import java.util.Map;

// BinarySelector

public class BinarySelector {

    private final Map<BinaryType, BinaryOperation<Double>> operations;

    @Inject
    public BinarySelector(Map<BinaryType, BinaryOperation<Double>> operations) {
        this.operations = operations;
    }

    public BinaryOperation<Double> get(BinaryType type) {
        BinaryOperation<Double> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No binary operation registered for: " + type);
        }
        return operation;
    }
}