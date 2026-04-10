package org.example.models;

import org.example.interfaces.PrintableCalculation;

/**
 * Represents the result of a unary calculation with typed operation, input, and result.
 *
 * @param <E> operation enum type
 * @param <I> input type
 * @param <R> result type
 * @param operation unary operation type
 * @param input input value
 * @param result calculated result
 */
public record UnaryCalculationRecord<E extends Enum<E>, I, R>(
        E operation,
        I input,
        R result
) implements PrintableCalculation {

    /**
     * Formats this unary calculation as a printable string.
     *
     * @return formatted calculation output
     */
    @Override
    public String format() {
        return operation + " " + input + " = " + result;
    }
}