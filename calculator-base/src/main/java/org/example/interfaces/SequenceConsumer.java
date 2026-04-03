package org.example.interfaces;

import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
public interface SequenceConsumer extends java.util.function.BiConsumer<List<Long>, Predicate<Long>> {

    @Override
    void accept(List<Long> sequence, Predicate<Long> predicate);

}