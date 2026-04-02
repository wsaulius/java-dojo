package org.example.model;

import java.util.List;

public class FibonacciSequence extends Number {

    private final List<Long> values;

    public FibonacciSequence(List<Long> values) {
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }

    @Override
    public int intValue() {
        return values.size();
    }

    @Override
    public long longValue() {
        return values.size();
    }

    @Override
    public float floatValue() {
        return values.size();
    }

    @Override
    public double doubleValue() {
        return values.size();
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
