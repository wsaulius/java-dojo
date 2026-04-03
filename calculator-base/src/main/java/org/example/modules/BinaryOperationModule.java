package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.BinaryType;
import org.example.implementations.binary.*;
import org.example.interfaces.BinaryOperation;

public class BinaryOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<BinaryType, BinaryOperation<Double>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<BinaryType>() {},
                        new TypeLiteral<BinaryOperation<Double>>() {}
                );

        binder.addBinding(BinaryType.ADD).to(AddOperation.class);
        binder.addBinding(BinaryType.SUBTRACT).to(SubtractOperation.class);
        binder.addBinding(BinaryType.MULTIPLY).to(MultiplyOperation.class);
        binder.addBinding(BinaryType.DIVIDE).to(DivideOperation.class);
        binder.addBinding(BinaryType.MODULO).to(ModuloOperation.class);
        binder.addBinding(BinaryType.POWER).to(PowerOperation.class);
        binder.addBinding(BinaryType.MAX).to(MaxOperation.class);
        binder.addBinding(BinaryType.MIN).to(MinOperation.class);
    }
}