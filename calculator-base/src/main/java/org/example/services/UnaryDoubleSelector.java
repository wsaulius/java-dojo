package org.example.services;

import jakarta.inject.Inject;
import org.example.enums.UnaryDoubleType;
import org.example.interfaces.UnaryOperation;

import java.util.Map;

public class UnaryDoubleSelector {

    private final Map<UnaryDoubleType, UnaryOperation<Integer, Double>> operations;

    @Inject
    public UnaryDoubleSelector(Map<UnaryDoubleType, UnaryOperation<Integer, Double>> operations) {
        this.operations = operations;
    }

    public UnaryOperation<Integer, Double> get(UnaryDoubleType type) {
        UnaryOperation<Integer, Double> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary double operation registered for: " + type);
        }
        return operation;
    }
}