package org.example.modules;

import com.google.inject.AbstractModule;

public class CalculatorApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new UnaryIntOperationModule());
        install(new UnaryDoubleOperationModule());
        install(new UnaryLongOperationModule());
        install(new UnaryBooleanOperationModule());
        install(new UnaryBigIntegerOperationModule());
        install(new BinaryOperationModule());
        install(new SequenceModule());
        install(new SelectorModule());
        install(new CalculatorConsumerModule());
        install(new ExecutorModule());
    }
}