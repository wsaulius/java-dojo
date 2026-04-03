package org.example.interfaces;

import java.util.function.Consumer;

@FunctionalInterface
public interface CalculationConsumer<T> extends Consumer<T> {
    @Override
    void accept(T calculation);
}