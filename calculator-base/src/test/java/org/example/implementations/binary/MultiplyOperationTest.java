package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplyOperationTest {

    private final MultiplyOperation operation = new MultiplyOperation();

    @Test
    void shouldMultiplyTwoPositiveNumbers() {
        assertEquals(15.0, operation.applyAsDouble(5.0, 3.0));
    }

    @Test
    void shouldMultiplyPositiveAndNegative() {
        assertEquals(-15.0, operation.applyAsDouble(5.0, -3.0));
    }

    @Test
    void shouldMultiplyTwoNegativeNumbers() {
        assertEquals(15.0, operation.applyAsDouble(-5.0, -3.0));
    }

    @Test
    void shouldReturnZeroWhenOneOperandIsZero() {
        assertEquals(0.0, operation.applyAsDouble(5.0, 0.0));
        assertEquals(0.0, operation.applyAsDouble(0.0, 5.0));
    }
}