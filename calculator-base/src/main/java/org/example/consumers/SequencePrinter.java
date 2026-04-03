package org.example.consumers;

import org.example.interfaces.SequenceConsumer;

import java.util.List;
import java.util.function.Predicate;

public class SequencePrinter<T> implements SequenceConsumer<T> {

    @Override
    public void accept(List<T> sequence, Predicate<T> predicate) {
        sequence.stream()
                .filter(predicate)
                .forEach(System.out::println);
    }
}