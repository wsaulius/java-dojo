package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CubeOperationTest {

    private final CubeOperation operation = new CubeOperation();

    @Test
    void shouldCubePositiveNumber() {
        assertEquals(27, operation.applyAsInt(3));
    }

    @Test
    void shouldCubeNegativeNumber() {
        assertEquals(-27, operation.applyAsInt(-3));
    }

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(0, operation.applyAsInt(0));
    }
}