package org.example.predicates.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangePredicateTest {

    @Test
    void shouldReturnTrueForValuesInsideRange() {
        RangePredicate predicate = new RangePredicate(1, 5);

        assertTrue(predicate.test(1L));
        assertTrue(predicate.test(3L));
        assertTrue(predicate.test(5L));
    }

    @Test
    void shouldReturnFalseForValuesOutsideRange() {
        RangePredicate predicate = new RangePredicate(1, 5);

        assertFalse(predicate.test(0L));
        assertFalse(predicate.test(6L));
    }

    @Test
    void shouldReturnFalseForNull() {
        RangePredicate predicate = new RangePredicate(1, 5);

        assertFalse(predicate.test(null));
    }

    @Test
    void shouldWorkWithNegativeRange() {
        RangePredicate predicate = new RangePredicate(-5, -1);

        assertTrue(predicate.test(-3L));
        assertFalse(predicate.test(0L));
    }
}