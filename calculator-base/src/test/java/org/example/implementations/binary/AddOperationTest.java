package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddOperationTest {

    private final AddOperation operation = new AddOperation();

    @Test
    void shouldAddTwoPositiveNumbers() {
        assertEquals(8.0, operation.applyAsDouble(5.0, 3.0));
    }

    @Test
    void shouldAddPositiveAndNegative() {
        assertEquals(2.0, operation.applyAsDouble(5.0, -3.0));
    }

    @Test
    void shouldAddTwoNegativeNumbers() {
        assertEquals(-8.0, operation.applyAsDouble(-5.0, -3.0));
    }

    @Test
    void shouldAddWithZero() {
        assertEquals(5.0, operation.applyAsDouble(5.0, 0.0));
        assertEquals(5.0, operation.applyAsDouble(0.0, 5.0));
    }

    @Test
    void shouldReturnZeroWhenBothZero() {
        assertEquals(0.0, operation.applyAsDouble(0.0, 0.0));
    }
}