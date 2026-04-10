package org.example.interfaces;

import java.util.function.Consumer;

/**
 * Represents a typed consumer for completed calculation objects.
 *
 * @param <T> calculation type consumed by this interface
 */
@FunctionalInterface
public interface CalculationConsumer<T> extends Consumer<T> {

    /**
     * Consumes a calculation object.
     *
     * @param calculation calculation to consume
     */
    @Override
    void accept(T calculation);
}