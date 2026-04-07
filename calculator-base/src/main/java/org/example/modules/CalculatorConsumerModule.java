package org.example.modules;

import com.google.inject.AbstractModule;
import org.example.consumers.CalculationPrinter;
import org.example.consumers.ResultPrinter;
import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.ResultConsumer;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class CalculatorConsumerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<ResultConsumer<Integer>>() {})
                .to(new TypeLiteral<ResultPrinter<Integer>>() {});

        bind(new TypeLiteral<ResultConsumer<Double>>() {})
                .to(new TypeLiteral<ResultPrinter<Double>>() {});

        bind(new TypeLiteral<ResultConsumer<Long>>() {})
                .to(new TypeLiteral<ResultPrinter<Long>>() {});

        bind(new TypeLiteral<ResultConsumer<Boolean>>() {})
                .to(new TypeLiteral<ResultPrinter<Boolean>>() {});
    }
}