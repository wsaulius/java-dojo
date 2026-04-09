package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtractOperationTest {

    private final SubtractOperation operation = new SubtractOperation();

    @Test
    void shouldSubtractTwoPositiveNumbers() {
        assertEquals(2.0, operation.applyAsDouble(5.0, 3.0));
    }

    @Test
    void shouldSubtractNegativeNumber() {
        assertEquals(8.0, operation.applyAsDouble(5.0, -3.0));
    }

    @Test
    void shouldSubtractTwoNegativeNumbers() {
        assertEquals(-2.0, operation.applyAsDouble(-5.0, -3.0));
    }

    @Test
    void shouldReturnSameNumberWhenSubtractingZero() {
        assertEquals(5.0, operation.applyAsDouble(5.0, 0.0));
    }
}