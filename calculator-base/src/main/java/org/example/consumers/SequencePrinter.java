package org.example.consumers;

import jakarta.inject.Singleton;
import org.example.interfaces.SequenceConsumer;

import java.util.List;
import java.util.function.Predicate;

@Singleton
public final class SequencePrinter<T> implements SequenceConsumer<T> {

    @Override
    public void accept(List<T> sequence, Predicate<T> predicate) {
        sequence.stream()
                .filter(predicate)
                .forEach(System.out::println);
    }
}