package org.example.interfaces;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@FunctionalInterface
public interface SequenceConsumer<T> extends BiConsumer<List<T>, Predicate<T>> {
    @Override
    void accept(List<T> sequence, Predicate<T> predicate);
}