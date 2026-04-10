package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.BinaryType;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;

/**
 * Selects a registered {@link DoubleBinaryOperator} for a given {@link BinaryType}.
 */
@Singleton
public final class BinarySelector {

    private final Map<BinaryType, DoubleBinaryOperator> operations;

    /**
     * Creates a selector with injected binary operation mappings.
     *
     * @param operations map of binary operation types to operator implementations
     */
    @Inject
    public BinarySelector(Map<BinaryType, DoubleBinaryOperator> operations) {
        this.operations = operations;
    }

    /**
     * Returns the operator registered for the given binary operation type.
     *
     * @param type binary operation type
     * @return matching binary operator
     * @throws IllegalArgumentException if no operator is registered for the given type
     */
    public DoubleBinaryOperator get(BinaryType type) {
        DoubleBinaryOperator operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No binary operation registered for: " + type);
        }
        return operation;
    }
}