package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryLongType;

import java.util.Map;
import java.util.function.IntToLongFunction;

/**
 * Selects a registered {@link IntToLongFunction} for a given {@link UnaryLongType}.
 */
@Singleton
public final class UnaryLongSelector {

    private final Map<UnaryLongType, IntToLongFunction> operations;

    /**
     * Creates a selector with injected unary long operation mappings.
     *
     * @param operations map of unary long operation types to function implementations
     */
    @Inject
    public UnaryLongSelector(Map<UnaryLongType, IntToLongFunction> operations) {
        this.operations = operations;
    }

    /**
     * Returns the function registered for the given unary long operation type.
     *
     * @param type unary long operation type
     * @return matching unary long function
     * @throws IllegalArgumentException if no function is registered for the given type
     */
    public IntToLongFunction get(UnaryLongType type) {
        IntToLongFunction operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary long operation registered for: " + type);
        }
        return operation;
    }
}