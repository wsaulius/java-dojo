package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryIntType;
import org.example.implementations.unary.*;
import org.example.interfaces.UnaryOperation;

public class UnaryIntOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryIntType, UnaryOperation<Integer, Integer>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryIntType>() {},
                        new TypeLiteral<UnaryOperation<Integer, Integer>>() {}
                );

        binder.addBinding(UnaryIntType.SQUARE).to(SquareOperation.class);
        binder.addBinding(UnaryIntType.CUBE).to(CubeOperation.class);
        binder.addBinding(UnaryIntType.NEGATE).to(NegateOperation.class);
        binder.addBinding(UnaryIntType.ABS).to(AbsOperation.class);
    }
}