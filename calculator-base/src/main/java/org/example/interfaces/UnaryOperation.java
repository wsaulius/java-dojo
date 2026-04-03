package org.example.interfaces;

@FunctionalInterface
public interface UnaryOperation<I, O> {
    O apply(I input);
}