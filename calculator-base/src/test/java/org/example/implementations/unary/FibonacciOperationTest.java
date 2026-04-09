package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FibonacciOperationTest {

    private final FibonacciOperation operation = new FibonacciOperation();

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(BigInteger.ZERO, operation.apply(0));
    }

    @Test
    void shouldReturnOneForOne() {
        assertEquals(BigInteger.ONE, operation.apply(1));
    }

    @Test
    void shouldCalculateFibonacciValue() {
        assertEquals(BigInteger.valueOf(55), operation.apply(10));
    }

    @Test
    void shouldThrowForNegativeNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operation.apply(-1)
        );
        assertEquals("Negative not allowed", exception.getMessage());
    }
}