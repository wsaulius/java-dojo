package org.example.predicates.sequence;

import java.util.function.Predicate;

//TO BE IMPLEMENTED
public class RangePredicate implements Predicate<Long> {
    private final long min;
    private final long max;

    public RangePredicate(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(Long n) {
        return n != null && n >= min && n <= max;
    }
}