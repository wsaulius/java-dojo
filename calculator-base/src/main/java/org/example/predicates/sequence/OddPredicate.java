package org.example.predicates.sequence;

import java.util.function.Predicate;

//TO BE IMPLEMENTED
public class OddPredicate implements Predicate<Long> {
    @Override
    public boolean test(Long n) {
        return n % 2 != 0;
    }
}