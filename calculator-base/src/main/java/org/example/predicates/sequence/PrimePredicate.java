package org.example.predicates.sequence;

import java.util.function.Predicate;

/**
 * Predicate that checks whether a {@link Long} value is prime.
 */
public class PrimePredicate implements Predicate<Long> {

    /**
     * Evaluates whether the given value is a prime number.
     *
     * @param n value to evaluate
     * @return {@code true} if the value is prime, otherwise {@code false}
     */
    @Override
    public boolean test(Long n) {
        if (n == null || n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}