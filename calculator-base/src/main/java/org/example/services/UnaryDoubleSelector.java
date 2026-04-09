package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryDoubleType;

import java.util.Map;
import java.util.function.IntToDoubleFunction;

@Singleton
public class UnaryDoubleSelector {

    private final Map<UnaryDoubleType, IntToDoubleFunction> operations;

    @Inject
    public UnaryDoubleSelector(Map<UnaryDoubleType, IntToDoubleFunction> operations) {
        this.operations = operations;
    }

    public IntToDoubleFunction get(UnaryDoubleType type) {
        IntToDoubleFunction operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary double operation registered for: " + type);
        }
        return operation;
    }
}