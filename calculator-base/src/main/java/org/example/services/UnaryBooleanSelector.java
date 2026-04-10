package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryBooleanType;

import java.util.Map;
import java.util.function.IntPredicate;

/**
 * Selects a registered {@link IntPredicate} for a given {@link UnaryBooleanType}.
 */
@Singleton
public final class UnaryBooleanSelector {

    private final Map<UnaryBooleanType, IntPredicate> operations;

    /**
     * Creates a selector with injected unary boolean operation mappings.
     *
     * @param operations map of unary boolean operation types to predicate implementations
     */
    @Inject
    public UnaryBooleanSelector(Map<UnaryBooleanType, IntPredicate> operations) {
        this.operations = operations;
    }

    /**
     * Returns the predicate registered for the given unary boolean operation type.
     *
     * @param type unary boolean operation type
     * @return matching unary boolean predicate
     * @throws IllegalArgumentException if no predicate is registered for the given type
     */
    public IntPredicate get(UnaryBooleanType type) {
        IntPredicate operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary boolean operation registered for: " + type);
        }
        return operation;
    }
}