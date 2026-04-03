package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.BinaryType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.interfaces.SequenceConsumer;
import org.example.modules.*;
import org.example.services.CalculatorService;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new UnaryIntOperationModule(),
                new UnaryDoubleOperationModule(),
                new UnaryLongOperationModule(),
                new UnaryBooleanOperationModule(),
                new BinaryOperationModule(),
                new SequenceModule(),
                new SelectorModule()
        );

        CalculatorService calculator = injector.getInstance(CalculatorService.class);
        BiConsumer<List<Long>, Predicate<Long>> sequenceConsumer =
                injector.getInstance(Key.get(new TypeLiteral<SequenceConsumer>() {}));

        Predicate<Long> evenPredicate =
                injector.getInstance(new Key<Predicate<Long>>() {});

        sequenceConsumer.accept(List.of(1L, 2L, 3L, 4L, 5L, 6L), evenPredicate);

        System.out.println(calculator.runUnaryInt(UnaryIntType.CUBE, 5));
        System.out.println(calculator.runBinary(BinaryType.ADD, 10.0, 20.0));
        System.out.println(calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17));
        System.out.println(calculator.runUnaryLong(UnaryLongType.FIBONACCI, 17));


    }
}