package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryBooleanType;

import java.util.Map;
import java.util.function.IntPredicate;

@Singleton
public class UnaryBooleanSelector {

    private final Map<UnaryBooleanType, IntPredicate> operations;

    @Inject
    public UnaryBooleanSelector(Map<UnaryBooleanType, IntPredicate> operations) {
        this.operations = operations;
    }

    public IntPredicate get(UnaryBooleanType type) {
        IntPredicate operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary boolean operation registered for: " + type);
        }
        return operation;
    }
}