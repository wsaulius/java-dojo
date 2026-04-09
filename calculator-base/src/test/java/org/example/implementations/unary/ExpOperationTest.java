package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpOperationTest {

    private final ExpOperation operation = new ExpOperation();

    @Test
    void shouldReturnEToPowerZero() {
        assertEquals(1.0, operation.applyAsDouble(0), 1e-9);
    }

    @Test
    void shouldReturnEToPowerOne() {
        assertEquals(Math.E, operation.applyAsDouble(1), 1e-9);
    }

    @Test
    void shouldReturnEToPowerTwo() {
        assertEquals(Math.exp(2), operation.applyAsDouble(2), 1e-9);
    }
}