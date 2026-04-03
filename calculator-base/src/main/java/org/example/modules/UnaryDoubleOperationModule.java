package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryDoubleType;
import org.example.implementations.unary.ExpOperation;
import org.example.implementations.unary.LogOperation;
import org.example.implementations.unary.SqrtOperation;
import org.example.interfaces.UnaryOperation;

public class UnaryDoubleOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryDoubleType, UnaryOperation<Integer, Double>> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryDoubleType>() {},
                        new TypeLiteral<UnaryOperation<Integer, Double>>() {}
                );

        binder.addBinding(UnaryDoubleType.SQRT).to(SqrtOperation.class);
        binder.addBinding(UnaryDoubleType.LOG).to(LogOperation.class);
        binder.addBinding(UnaryDoubleType.EXP).to(ExpOperation.class);
    }
}