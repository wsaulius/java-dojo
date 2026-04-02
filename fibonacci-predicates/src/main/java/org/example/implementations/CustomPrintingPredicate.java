package org.example.implementations;

import java.util.function.Predicate;
public class CustomPrintingPredicate implements Predicate<Long> {

    private String description;
    private Predicate<Long> predicate;

    // Constructor
    public CustomPrintingPredicate(String description, Predicate<Long> predicate) {
        this.description = description;
        this.predicate = predicate;
    };

    @Override
    public boolean test(Long t) {
        return predicate.test(t);
    };

    @Override
    public String toString() {
        return description;
    }
}
