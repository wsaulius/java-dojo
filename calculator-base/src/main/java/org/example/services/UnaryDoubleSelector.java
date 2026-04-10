package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryDoubleType;

import java.util.Map;
import java.util.function.IntToDoubleFunction;

/**
 * Selects a registered {@link IntToDoubleFunction} for a given {@link UnaryDoubleType}.
 */
@Singleton
public final class UnaryDoubleSelector {

    private final Map<UnaryDoubleType, IntToDoubleFunction> operations;

    /**
     * Creates a selector with injected unary double operation mappings.
     *
     * @param operations map of unary double operation types to function implementations
     */
    @Inject
    public UnaryDoubleSelector(Map<UnaryDoubleType, IntToDoubleFunction> operations) {
        this.operations = operations;
    }

    /**
     * Returns the function registered for the given unary double operation type.
     *
     * @param type unary double operation type
     * @return matching unary double function
     * @throws IllegalArgumentException if no function is registered for the given type
     */
    public IntToDoubleFunction get(UnaryDoubleType type) {
        IntToDoubleFunction operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary double operation registered for: " + type);
        }
        return operation;
    }
}