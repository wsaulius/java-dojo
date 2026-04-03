package org.example.interfaces;

@FunctionalInterface
public interface BinaryOperation<T> {
    T apply(T left, T right);
}