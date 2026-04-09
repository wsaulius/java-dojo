package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerOperationTest {

    private final PowerOperation operation = new PowerOperation();

    @Test
    void shouldRaiseNumberToPositivePower() {
        assertEquals(8.0, operation.applyAsDouble(2.0, 3.0));
    }

    @Test
    void shouldReturnOneForExponentZero() {
        assertEquals(1.0, operation.applyAsDouble(5.0, 0.0));
    }

    @Test
    void shouldHandleNegativeBase() {
        assertEquals(-8.0, operation.applyAsDouble(-2.0, 3.0));
    }

    @Test
    void shouldHandleFractionalResultForNegativeExponent() {
        assertEquals(0.25, operation.applyAsDouble(2.0, -2.0));
    }
}