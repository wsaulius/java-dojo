package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryLongType;
import org.example.implementations.unary.FactorialOperation;
import org.example.implementations.unary.FibonacciOperation;
import org.example.interfaces.UnaryOperation;

public class UnaryLongOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryLongType, UnaryOperation<Integer, Long>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryLongType>() {},
                        new TypeLiteral<UnaryOperation<Integer, Long>>() {}
                );

        binder.addBinding(UnaryLongType.FACTORIAL).to(FactorialOperation.class);
        binder.addBinding(UnaryLongType.FIBONACCI).to(FibonacciOperation.class);
    }
}