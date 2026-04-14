package org.example.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnaryCalculationRecordTest {

    private enum TestOp {
        NEGATE
    }

    @Test
    void formatShouldReturnCorrectString() {
        UnaryCalculationRecord<TestOp, Integer, Integer> record =
                new UnaryCalculationRecord<>(TestOp.NEGATE, 5, -5);

        String result = record.format();

        assertEquals("NEGATE 5 = -5", result);
    }

    @Test
    void formatShouldHandleDifferentTypes() {
        UnaryCalculationRecord<TestOp, Double, Double> record =
                new UnaryCalculationRecord<>(TestOp.NEGATE, 2.5, -2.5);

        assertEquals("NEGATE 2.5 = -2.5", record.format());
    }
}