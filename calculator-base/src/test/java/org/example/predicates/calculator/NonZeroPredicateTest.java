package org.example.predicates.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonZeroPredicateTest {

    @Test
    void shouldReturnFalseForZero() {
        assertFalse(new NonZeroPredicate().test(0.0));
    }

    @Test
    void shouldReturnTrueForNonZero() {
        assertTrue(new NonZeroPredicate().test(3.5));
        assertTrue(new NonZeroPredicate().test(-2.0));
    }
}