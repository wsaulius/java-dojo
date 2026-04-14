package org.example.predicates.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OddPredicateTest {

    @Test
    void shouldReturnTrueForOddNumbers() {
        assertTrue(new OddPredicate().test(1L));
        assertTrue(new OddPredicate().test(-3L));
    }

    @Test
    void shouldReturnFalseForEvenNumbers() {
        assertFalse(new OddPredicate().test(2L));
        assertFalse(new OddPredicate().test(0L));
    }
}