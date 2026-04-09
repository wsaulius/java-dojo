package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogOperationTest {

    private final LogOperation operation = new LogOperation();

    @Test
    void shouldReturnZeroForOne() {
        assertEquals(0.0, operation.applyAsDouble(1), 1e-9);
    }

    @Test
    void shouldCalculateNaturalLog() {
        assertEquals(Math.log(10), operation.applyAsDouble(10), 1e-9);
    }

    @Test
    void shouldThrowForZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operation.applyAsDouble(0)
        );
        assertEquals("Log undefined for non-positive values", exception.getMessage());
    }

    @Test
    void shouldThrowForNegativeNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operation.applyAsDouble(-5)
        );
        assertEquals("Log undefined for non-positive values", exception.getMessage());
    }
}