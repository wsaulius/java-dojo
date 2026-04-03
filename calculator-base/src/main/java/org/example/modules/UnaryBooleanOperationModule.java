package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryBooleanType;
import org.example.implementations.unary.PrimeCheckOperation;
import org.example.interfaces.UnaryOperation;

public class UnaryBooleanOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryBooleanType, UnaryOperation<Integer, Boolean>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryBooleanType>() {},
                        new TypeLiteral<UnaryOperation<Integer, Boolean>>() {}
                );

        binder.addBinding(UnaryBooleanType.IS_PRIME).to(PrimeCheckOperation.class);
    }
}