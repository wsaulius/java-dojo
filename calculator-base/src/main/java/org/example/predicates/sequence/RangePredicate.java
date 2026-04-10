package org.example.predicates.sequence;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Long} value falls within
 * an inclusive range.
 */
public class RangePredicate implements Predicate<Long> {

    private final long min;
    private final long max;

    /**
     * Creates a range predicate with inclusive lower and upper bounds.
     *
     * @param min minimum allowed value
     * @param max maximum allowed value
     */
    public RangePredicate(long min, long max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Evaluates whether the given value is within the configured inclusive range.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is within range, otherwise {@code false}
     */
    @Override
    public boolean test(Long n) {
        return n != null && n >= min && n <= max;
    }
}