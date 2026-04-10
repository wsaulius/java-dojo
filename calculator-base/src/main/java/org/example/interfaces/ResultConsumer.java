package org.example.interfaces;

import java.util.function.Consumer;

/**
 * Represents a typed consumer for calculation result values.
 *
 * @param <T> result type consumed by this interface
 */
@FunctionalInterface
public interface ResultConsumer<T> extends Consumer<T> {

    /**
     * Consumes a result value.
     *
     * @param result result value to consume
     */
    @Override
    void accept(T result);
}