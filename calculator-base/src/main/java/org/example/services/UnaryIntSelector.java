package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryIntType;

import java.util.Map;
import java.util.function.IntUnaryOperator;

/**
 * Selects a registered {@link IntUnaryOperator} for a given {@link UnaryIntType}.
 */
@Singleton
public final class UnaryIntSelector {

    private final Map<UnaryIntType, IntUnaryOperator> operations;

    /**
     * Creates a selector with injected unary integer operation mappings.
     *
     * @param operations map of unary integer operation types to operator implementations
     */
    @Inject
    public UnaryIntSelector(Map<UnaryIntType, IntUnaryOperator> operations) {
        this.operations = operations;
    }

    /**
     * Returns the operator registered for the given unary integer operation type.
     *
     * @param type unary integer operation type
     * @return matching unary integer operator
     * @throws IllegalArgumentException if no operator is registered for the given type
     */
    public IntUnaryOperator get(UnaryIntType type) {
        IntUnaryOperator operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary int operation registered for: " + type);
        }
        return operation;
    }
}