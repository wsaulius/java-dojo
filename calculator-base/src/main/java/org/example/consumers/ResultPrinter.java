package org.example.consumers;

import org.example.interfaces.ResultConsumer;

public class ResultPrinter<T> implements ResultConsumer<T> {

    @Override
    public void accept(T result) {
        System.out.println(result);
    }
}