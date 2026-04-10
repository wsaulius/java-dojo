package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.example.enums.BinaryType;
import org.example.implementations.binary.*;
import java.util.function.DoubleBinaryOperator;
import org.example.implementations.binary.*;

/**
 * Guice module that binds each {@link BinaryType} to its corresponding
 * {@link DoubleBinaryOperator} implementation.
 */
public class BinaryOperationModule extends AbstractModule {

    /**
     * Configures binary operation bindings.
     */
    @Override
    protected void configure() {
        MapBinder<BinaryType, DoubleBinaryOperator> binder =
                MapBinder.newMapBinder(
                        binder(),
                        new TypeLiteral<BinaryType>() {},
                        new TypeLiteral<DoubleBinaryOperator>() {}
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