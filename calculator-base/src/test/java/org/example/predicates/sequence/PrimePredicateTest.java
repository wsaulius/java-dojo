package org.example.predicates.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimePredicateTest {

    @Test
    void shouldReturnFalseForNull() {
        assertFalse(new PrimePredicate().test(null));
    }

    @Test
    void shouldReturnFalseForNumbersLessThanTwo() {
        assertFalse(new PrimePredicate().test(0L));
        assertFalse(new PrimePredicate().test(1L));
        assertFalse(new PrimePredicate().test(-5L));
    }

    @Test
    void shouldReturnTrueForTwo() {
        assertTrue(new PrimePredicate().test(2L));
    }

    @Test
    void shouldReturnFalseForEvenNumbersGreaterThanTwo() {
        assertFalse(new PrimePredicate().test(4L));
        assertFalse(new PrimePredicate().test(10L));
    }

    @Test
    void shouldReturnFalseForOddCompositeNumbers() {
        assertFalse(new PrimePredicate().test(9L));
        assertFalse(new PrimePredicate().test(15L));
    }

    @Test
    void shouldReturnTrueForPrimeNumbers() {
        assertTrue(new PrimePredicate().test(3L));
        assertTrue(new PrimePredicate().test(5L));
        assertTrue(new PrimePredicate().test(11L));
    }
}