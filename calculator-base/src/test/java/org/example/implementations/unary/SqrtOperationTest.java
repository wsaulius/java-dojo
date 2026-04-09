package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SqrtOperationTest {

    private final SqrtOperation operation = new SqrtOperation();

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(0.0, operation.applyAsDouble(0), 1e-9);
    }

    @Test
    void shouldCalculateSquareRoot() {
        assertEquals(4.0, operation.applyAsDouble(16), 1e-9);
    }

    @Test
    void shouldCalculateNonIntegerSquareRoot() {
        assertEquals(Math.sqrt(2), operation.applyAsDouble(2), 1e-9);
    }

    @Test
    void shouldThrowForNegativeNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operation.applyAsDouble(-1)
        );
        assertEquals("Square root of negative number", exception.getMessage());
    }
}