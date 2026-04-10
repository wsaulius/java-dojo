package org.example.predicates.sequence;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Long} value is odd.
 */
public class OddPredicate implements Predicate<Long> {

    /**
     * Evaluates whether the given value is not evenly divisible by two.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is odd, otherwise {@code false}
     */
    @Override
    public boolean test(Long n) {
        return n % 2 != 0;
    }
}