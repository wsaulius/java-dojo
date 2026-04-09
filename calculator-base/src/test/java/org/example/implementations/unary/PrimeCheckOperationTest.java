package org.example.implementations.unary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeCheckOperationTest {

    private final PrimeCheckOperation operation = new PrimeCheckOperation();

    @Test
    void shouldReturnFalseForNumbersLessThanTwo() {
        assertFalse(operation.test(0));
        assertFalse(operation.test(1));
        assertFalse(operation.test(-7));
    }

    @Test
    void shouldReturnTrueForTwo() {
        assertTrue(operation.test(2));
    }

    @Test
    void shouldReturnTrueForPrimeOddNumber() {
        assertTrue(operation.test(13));
    }

    @Test
    void shouldReturnFalseForEvenCompositeNumber() {
        assertFalse(operation.test(10));
    }

    @Test
    void shouldReturnFalseForOddCompositeNumber() {
        assertFalse(operation.test(15));
    }
}