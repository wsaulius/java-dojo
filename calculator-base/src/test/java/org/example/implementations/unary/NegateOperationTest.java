package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NegateOperationTest {

    private final NegateOperation operation = new NegateOperation();

    @Test
    void shouldNegatePositiveNumber() {
        assertEquals(-5, operation.applyAsInt(5));
    }

    @Test
    void shouldNegateNegativeNumber() {
        assertEquals(5, operation.applyAsInt(-5));
    }

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(0, operation.applyAsInt(0));
    }
}