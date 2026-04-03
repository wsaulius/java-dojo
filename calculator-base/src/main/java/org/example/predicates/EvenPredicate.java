package org.example.predicates;

import java.util.function.Predicate;

public class EvenPredicate implements Predicate<Long> {
    @Override
    public boolean test(Long n) {
        return n % 2 == 0;
    }
}