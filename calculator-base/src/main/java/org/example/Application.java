package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.*;
import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.ResultConsumer;
import org.example.interfaces.SequenceConsumer;
import org.example.modules.*;
import org.example.services.CalculatorService;
import org.example.suppliers.ListSupplier;
import org.example.suppliers.RandomNumberSupplier;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new UnaryIntOperationModule(),
                new UnaryDoubleOperationModule(),
                new UnaryLongOperationModule(),
                new UnaryBooleanOperationModule(),
                new BinaryOperationModule(),
                new SequenceModule(),
                new SelectorModule(),
                new CalculatorConsumerModule(),
                new UnaryBigIntegerOperationModule()
        );

        CalculatorService calculator = injector.getInstance(CalculatorService.class);

        //Sequence Supplier example declaration and functionality
        ListSupplier<Long> listSupplier = new ListSupplier<>(() -> 500L, 10);

        //Random number list supplier declaration and functionality
        Supplier<List<Long>> randomListSupplier =
                new ListSupplier<>(() -> ThreadLocalRandom.current().nextLong(0, 100), 5);




        //Result Consumer example
        ResultConsumer<Integer> intConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Integer>>() {}));

        intConsumer.accept(calculator.runUnaryInt(UnaryIntType.CUBE, 5));

        //Sequence Consumer example
        SequenceConsumer<Long> sequenceConsumer =
                injector.getInstance(Key.get(new TypeLiteral<SequenceConsumer<Long>>() {}));

        Predicate<Long> evenPredicate =
                injector.getInstance(new Key<Predicate<Long>>() {});

        sequenceConsumer.accept(randomListSupplier.get(), evenPredicate);




        //Calculator Functionality
        System.out.println(calculator.runUnaryInt(UnaryIntType.CUBE, 5));
        System.out.println(calculator.runBinary(BinaryType.ADD, 10.0, 20.0));
        System.out.println(calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17));
        System.out.println(calculator.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 500));


    }
}