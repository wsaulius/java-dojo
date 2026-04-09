package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.UnaryBigIntegerType;
import java.util.Map;
import java.math.BigInteger;
import java.util.function.Function;

@Singleton
public class UnaryBigIntegerSelector {

    private final Map<UnaryBigIntegerType, Function<Integer, BigInteger>> operations;

    @Inject
    public UnaryBigIntegerSelector(Map<UnaryBigIntegerType, Function<Integer, BigInteger>> operations) {
        this.operations = operations;
    }

    public Function<Integer, BigInteger> get(UnaryBigIntegerType type) {
        Function<Integer, BigInteger> operation = operations.get(type);
        if (operation == null) {
            throw new IllegalArgumentException("No unary BigInteger operation registered for: " + type);
        }
        return operation;
    }
}