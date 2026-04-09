package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactorialOperationTest {

    private final FactorialOperation operation = new FactorialOperation();

    @Test
    void shouldReturnOneForZero() {
        assertEquals(1L, operation.applyAsLong(0));
    }

    @Test
    void shouldReturnOneForOne() {
        assertEquals(1L, operation.applyAsLong(1));
    }

    @Test
    void shouldCalculateFactorial() {
        assertEquals(120L, operation.applyAsLong(5));
    }

    @Test
    void shouldThrowForNegativeNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operation.applyAsLong(-1)
        );
        assertEquals("Negative not allowed", exception.getMessage());
    }
}