package org.example.predicates.sequence;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Long} value is even.
 */
public class EvenPredicate implements Predicate<Long> {

    /**
     * Evaluates whether the given value is evenly divisible by two.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is even, otherwise {@code false}
     */
    @Override
    public boolean test(Long n) {
        return n % 2 == 0;
    }
}