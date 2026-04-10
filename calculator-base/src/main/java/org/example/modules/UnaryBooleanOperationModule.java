package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryBooleanType;
import org.example.implementations.unary.PrimeCheckOperation;

import java.util.function.IntPredicate;

/**
 * Guice module that binds each {@link UnaryBooleanType} to its corresponding
 * {@link IntPredicate} implementation.
 */
public class UnaryBooleanOperationModule extends AbstractModule {

    /**
     * Configures unary boolean operation bindings.
     */
    @Override
    protected void configure() {
        MapBinder<UnaryBooleanType, IntPredicate> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryBooleanType>() {},
                        new TypeLiteral<IntPredicate>() {}
                );

        binder.addBinding(UnaryBooleanType.IS_PRIME).to(PrimeCheckOperation.class);
    }
}