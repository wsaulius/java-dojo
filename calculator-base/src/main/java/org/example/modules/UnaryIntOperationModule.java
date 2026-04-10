package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryIntType;
import org.example.implementations.unary.*;

import java.util.function.IntUnaryOperator;

/**
 * Guice module that binds each {@link UnaryIntType} to its corresponding
 * {@link IntUnaryOperator} implementation.
 */
public class UnaryIntOperationModule extends AbstractModule {

    /**
     * Configures unary integer operation bindings.
     */
    @Override
    protected void configure() {
        MapBinder<UnaryIntType, IntUnaryOperator> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryIntType>() {},
                        new TypeLiteral<IntUnaryOperator>() {}
                );

        binder.addBinding(UnaryIntType.SQUARE).to(SquareOperation.class);
        binder.addBinding(UnaryIntType.CUBE).to(CubeOperation.class);
        binder.addBinding(UnaryIntType.NEGATE).to(NegateOperation.class);
        binder.addBinding(UnaryIntType.ABS).to(AbsOperation.class);
    }
}