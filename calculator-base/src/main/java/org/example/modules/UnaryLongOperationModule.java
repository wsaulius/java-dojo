package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryLongType;
import org.example.implementations.unary.FactorialOperation;
import org.example.implementations.unary.FibonacciOperation;

import java.util.function.IntToLongFunction;

public class UnaryLongOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryLongType, IntToLongFunction> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryLongType>() {},
                        new TypeLiteral<IntToLongFunction>() {}
                );

        binder.addBinding(UnaryLongType.FACTORIAL).to(FactorialOperation.class);
    }
}