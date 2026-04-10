package org.example.consumers;

import jakarta.inject.Singleton;
import org.example.interfaces.ResultConsumer;

/**
 * Prints a single result value.
 */
@Singleton
public final class ResultPrinter<T> implements ResultConsumer<T> {

    /** {@inheritDoc} */
    @Override
    public void accept(T result) {
        System.out.println(result);
    }
}