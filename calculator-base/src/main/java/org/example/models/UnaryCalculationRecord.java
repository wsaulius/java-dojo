package org.example.models;

import org.example.interfaces.PrintableCalculation;

public record UnaryCalculationRecord<E extends Enum<E>, I, R>(
        E operation,
        I input,
        R result
) implements PrintableCalculation {

    @Override
    public String format() {
        return operation + " " + input + " = " + result;
    }
}