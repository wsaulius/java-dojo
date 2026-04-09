package org.example.consumers;

import jakarta.inject.Singleton;
import org.example.interfaces.ResultConsumer;

@Singleton
public final class ResultPrinter<T> implements ResultConsumer<T> {

    @Override
    public void accept(T result) {
        System.out.println(result);
    }
}