package org.example.predicates.calculator;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Double} value is not zero.
 */
public class NonZeroPredicate implements Predicate<Double> {

    /**
     * Evaluates whether the given value is different from zero.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is not zero, otherwise {@code false}
     */
    @Override
    public boolean test(Double n) {
        return n != 0;
    }
}