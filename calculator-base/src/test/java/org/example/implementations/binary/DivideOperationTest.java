package org.example.implementations.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivideOperationTest {

    private final DivideOperation operation = new DivideOperation();

    @Test
    void shouldDivideTwoPositiveNumbers() {
        assertEquals(2.0, operation.applyAsDouble(6.0, 3.0));
    }

    @Test
    void shouldDividePositiveAndNegative() {
        assertEquals(-2.0, operation.applyAsDouble(6.0, -3.0));
    }

    @Test
    void shouldDivideZeroByNonZero() {
        assertEquals(0.0, operation.applyAsDouble(0.0, 5.0));
    }

    @Test
    void shouldThrowWhenDividingByZero() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> operation.applyAsDouble(10.0, 0.0)
        );
        assertEquals("Division by zero", exception.getMessage());
    }
}