package org.example.interfaces;

@FunctionalInterface
public interface FibonacciOperation<T extends Number> {
    T apply(int n);
}
