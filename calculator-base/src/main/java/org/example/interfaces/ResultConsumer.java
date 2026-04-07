package org.example.interfaces;

import java.util.function.Consumer;

@FunctionalInterface
public interface ResultConsumer<T> extends Consumer<T> {
    @Override
    void accept(T result);
}