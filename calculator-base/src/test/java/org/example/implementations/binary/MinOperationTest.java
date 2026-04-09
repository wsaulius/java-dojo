package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinOperationTest {

    private final MinOperation operation = new MinOperation();

    @Test
    void shouldReturnLeftWhenLeftIsSmaller() {
        assertEquals(3.0, operation.applyAsDouble(3.0, 10.0));
    }

    @Test
    void shouldReturnRightWhenRightIsSmaller() {
        assertEquals(3.0, operation.applyAsDouble(10.0, 3.0));
    }

    @Test
    void shouldReturnSameValueWhenEqual() {
        assertEquals(5.0, operation.applyAsDouble(5.0, 5.0));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(-5.0, operation.applyAsDouble(-5.0, -3.0));
    }
}