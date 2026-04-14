package org.example.predicates.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvenPredicateTest {

    @Test
    void shouldReturnTrueForEvenNumbers() {
        assertTrue(new EvenPredicate().test(2L));
        assertTrue(new EvenPredicate().test(0L));
        assertTrue(new EvenPredicate().test(-4L));
    }

    @Test
    void shouldReturnFalseForOddNumbers() {
        assertFalse(new EvenPredicate().test(1L));
        assertFalse(new EvenPredicate().test(-3L));
    }
}