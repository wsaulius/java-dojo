package org.example.predicates.sequence;

import java.util.function.Predicate;

//CURRENTY IN USE JUST FOR MOCK TEST, NO REAL USAGE
public class EvenPredicate implements Predicate<Long> {
    @Override
    public boolean test(Long n) {
        return n % 2 == 0;
    }
}