package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryBigIntegerType;
import org.example.implementations.unary.FibonacciOperation;

import java.math.BigInteger;
import java.util.function.Function;

public class UnaryBigIntegerOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryBigIntegerType, Function<Integer, BigInteger>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryBigIntegerType>() {},
                        new TypeLiteral<Function<Integer, BigInteger>>() {}
                );

        binder.addBinding(UnaryBigIntegerType.FIBONACCI).to(FibonacciOperation.class);
    }
}