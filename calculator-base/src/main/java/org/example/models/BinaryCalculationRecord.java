package org.example.models;

import org.example.enums.BinaryType;
import org.example.interfaces.PrintableCalculation;

public record BinaryCalculationRecord(
        BinaryType operation,
        double left,
        double right,
        double result
) implements PrintableCalculation {

    @Override
    public String format() {
        return left + " " + operation + " " + right + " = " + result;
    }
}