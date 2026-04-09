package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModuloOperationTest {

    private final ModuloOperation operation = new ModuloOperation();

    @Test
    void shouldReturnRemainderForPositiveNumbers() {
        assertEquals(1.0, operation.applyAsDouble(10.0, 3.0));
    }

    @Test
    void shouldHandleNegativeDividend() {
        assertEquals(-1.0, operation.applyAsDouble(-10.0, 3.0));
    }

    @Test
    void shouldReturnZeroWhenEvenlyDivisible() {
        assertEquals(0.0, operation.applyAsDouble(12.0, 4.0));
    }

    @Test
    void shouldThrowWhenModuloByZero() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> operation.applyAsDouble(10.0, 0.0)
        );
        assertEquals("Modulo by zero", exception.getMessage());
    }
}