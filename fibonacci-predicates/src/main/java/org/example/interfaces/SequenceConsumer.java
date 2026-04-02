package org.example.interfaces;

import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface

public interface SequenceConsumer {
    void accept(List<Long> sequence, final Predicate<Long> predicate);
}
