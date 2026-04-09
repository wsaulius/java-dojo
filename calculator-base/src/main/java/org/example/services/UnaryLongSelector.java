package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryLongType;

import java.util.Map;
import java.util.function.IntToLongFunction;

@Singleton
public class UnaryLongSelector {

    private final Map<UnaryLongType, IntToLongFunction> operations;

    @Inject
    public UnaryLongSelector(Map<UnaryLongType, IntToLongFunction> operations) {
        this.operations = operations;
    }

    public IntToLongFunction get(UnaryLongType type) {
        IntToLongFunction operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary long operation registered for: " + type);
        }
        return operation;
    }
}