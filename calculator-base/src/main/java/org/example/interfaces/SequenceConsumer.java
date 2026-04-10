package org.example.interfaces;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * Represents a typed consumer for sequences together with a predicate applied to sequence elements.
 *
 * @param <T> sequence element type
 */
@FunctionalInterface
public interface SequenceConsumer<T> extends BiConsumer<List<T>, Predicate<T>> {

    /**
     * Consumes a sequence together with its predicate.
     *
     * @param sequence sequence to consume
     * @param predicate predicate associated with the sequence
     */
    @Override
    void accept(List<T> sequence, Predicate<T> predicate);
}