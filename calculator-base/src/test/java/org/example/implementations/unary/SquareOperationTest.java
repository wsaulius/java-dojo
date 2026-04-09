package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareOperationTest {

    private final SquareOperation operation = new SquareOperation();

    @Test
    void shouldSquarePositiveNumber() {
        assertEquals(25, operation.applyAsInt(5));
    }

    @Test
    void shouldSquareNegativeNumber() {
        assertEquals(25, operation.applyAsInt(-5));
    }

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(0, operation.applyAsInt(0));
    }
}