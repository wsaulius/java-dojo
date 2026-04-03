package org.example.predicates.calculator;

import java.util.function.Predicate;

//TO BE IMPLEMENTED
public class NonNegativePredicate implements Predicate<Double> {
    public boolean test(Double n) {
        return n >= 0;
    }
}