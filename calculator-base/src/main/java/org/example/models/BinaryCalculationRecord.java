package org.example.models;

import org.example.enums.BinaryType;
import org.example.interfaces.PrintableCalculation;

/**
 * Represents the result of a binary calculation with two numeric operands.
 *
 * @param operation binary operation type
 * @param left left operand
 * @param right right operand
 * @param result calculated result
 */
public record BinaryCalculationRecord(
        BinaryType operation,
        double left,
        double right,
        double result
) implements PrintableCalculation {

    /**
     * Formats this binary calculation as a printable string.
     *
     * @return formatted calculation output
     */
    @Override
    public String format() {
        return left + " " + operation + " " + right + " = " + result;
    }
}