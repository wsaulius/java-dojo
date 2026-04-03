package org.example.consumers;

import org.example.interfaces.SequenceConsumer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SequencePrinter implements SequenceConsumer {
    @Override
    public void accept(List<Long> sequence, Predicate<Long> predicate) {
        sequence.stream()
                .filter(predicate)
                .forEach(System.out::println);
    }
}