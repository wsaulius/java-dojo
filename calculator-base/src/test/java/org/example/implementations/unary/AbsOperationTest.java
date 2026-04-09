package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbsOperationTest {

    private final AbsOperation operation = new AbsOperation();

    @Test
    void shouldReturnSameValueForPositiveNumber() {
        assertEquals(5, operation.applyAsInt(5));
    }

    @Test
    void shouldConvertNegativeNumberToPositive() {
        assertEquals(5, operation.applyAsInt(-5));
    }

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(0, operation.applyAsInt(0));
    }
}