package org.example.models;

public record CalculationRecord(
        double left,
        double right,
        String operation,
        double result
) {}