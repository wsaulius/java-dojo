package org.example.predicates.calculator;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Double} value is positive.
 */
public class PositivePredicate implements Predicate<Double> {

    /**
     * Evaluates whether the given value is greater than zero.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is positive, otherwise {@code false}
     */
    @Override
    public boolean test(Double n) {
        return n > 0;
    }
}