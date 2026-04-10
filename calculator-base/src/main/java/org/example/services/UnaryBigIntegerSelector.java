package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryBigIntegerType;
import java.util.Map;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * Selects a registered {@link Function} for a given {@link UnaryBigIntegerType}.
 */
@Singleton
public final class UnaryBigIntegerSelector {

    private final Map<UnaryBigIntegerType, Function<Integer, BigInteger>> operations;

    /**
     * Creates a selector with injected unary BigInteger operation mappings.
     *
     * @param operations map of unary BigInteger operation types to function implementations
     */
    @Inject
    public UnaryBigIntegerSelector(Map<UnaryBigIntegerType, Function<Integer, BigInteger>> operations) {
        this.operations = operations;
    }

    /**
     * Returns the function registered for the given unary BigInteger operation type.
     *
     * @param type unary BigInteger operation type
     * @return matching unary BigInteger function
     * @throws IllegalArgumentException if no function is registered for the given type
     */
    public Function<Integer, BigInteger> get(UnaryBigIntegerType type) {
        Function<Integer, BigInteger> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary BigInteger operation registered for: " + type);
        }
        return operation;
    }
}