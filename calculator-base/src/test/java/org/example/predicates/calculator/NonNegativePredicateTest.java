package org.example.predicates.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonNegativePredicateTest {

    @Test
    void shouldReturnTrueForZero() {
        assertTrue(new NonNegativePredicate().test(0.0));
    }

    @Test
    void shouldReturnTrueForPositive() {
        assertTrue(new NonNegativePredicate().test(5.0));
    }

    @Test
    void shouldReturnFalseForNegative() {
        assertFalse(new NonNegativePredicate().test(-1.0));
    }
}