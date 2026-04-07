package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.UnaryDoubleType;
import org.example.implementations.unary.ExpOperation;
import org.example.implementations.unary.LogOperation;
import org.example.implementations.unary.SqrtOperation;

import java.util.function.IntToDoubleFunction;

public class UnaryDoubleOperationModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UnaryDoubleType, IntToDoubleFunction> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<UnaryDoubleType>() {},
                        new TypeLiteral<IntToDoubleFunction>() {}
                );

        binder.addBinding(UnaryDoubleType.SQRT).to(SqrtOperation.class);
        binder.addBinding(UnaryDoubleType.LOG).to(LogOperation.class);
        binder.addBinding(UnaryDoubleType.EXP).to(ExpOperation.class);
    }
}