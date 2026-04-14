package org.example.predicates.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositivePredicateTest {

    @Test
    void shouldReturnTrueForPositive() {
        assertTrue(new PositivePredicate().test(1.0));
    }

    @Test
    void shouldReturnFalseForZero() {
        assertFalse(new PositivePredicate().test(0.0));
    }

    @Test
    void shouldReturnFalseForNegative() {
        assertFalse(new PositivePredicate().test(-1.0));
    }
}