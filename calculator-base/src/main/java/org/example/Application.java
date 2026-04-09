package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import org.example.consumers.MatrixPrinter;
import org.example.enums.*;
import org.example.factories.CalculationConsumerResolver;
import org.example.implementations.binary.MatrixAddOperation;
import org.example.implementations.binary.MatrixDivideOperation;
import org.example.implementations.binary.MatrixMultiplyOperation;
import org.example.interfaces.ResultConsumer;
import org.example.interfaces.SequenceConsumer;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.*;
import org.example.services.CalculatorService;
import org.example.services.MatrixService;
import org.example.suppliers.ListSupplier;
import org.example.suppliers.MatrixSupplier;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Application {

    public static void main(String[] args) {

        // 🔹 Dependency Injection setup (Guice)
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
        CalculationConsumerResolver resolver = injector.getInstance(CalculationConsumerResolver.class);

        // 🔹 Unary INT example
        int intResult = calculator.runUnaryInt(UnaryIntType.CUBE, 5);
        resolver.unaryInt().accept(new UnaryCalculationRecord<>(UnaryIntType.CUBE, 5, intResult));

        // 🔹 Binary example
        double binaryResult = calculator.runBinary(BinaryType.ADD, 10.0, 20.0);
        resolver.binary().accept(new BinaryCalculationRecord(BinaryType.ADD, 10.0, 20.0, binaryResult));

        // 🔹 Boolean example
        boolean booleanResult = calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17);
        resolver.unaryBoolean().accept(
                new UnaryCalculationRecord<>(UnaryBooleanType.IS_PRIME, 17, booleanResult)
        );

        // 🔹 BigInteger example
        BigInteger bigResult =
                calculator.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 50);

        resolver.unaryBigInteger().accept(
                new UnaryCalculationRecord<>(UnaryBigIntegerType.FIBONACCI, 50, bigResult)
        );

        Supplier<List<Long>> randomListSupplier =
                new ListSupplier<>(() -> ThreadLocalRandom.current().nextLong(0, 100), 5);

        SequenceConsumer<Long> sequenceConsumer =
                injector.getInstance(Key.get(new TypeLiteral<SequenceConsumer<Long>>() {}));

        Predicate<Long> evenPredicate =
                injector.getInstance(new Key<Predicate<Long>>() {});

        sequenceConsumer.accept(randomListSupplier.get(), evenPredicate);

        ResultConsumer<Integer> intConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Integer>>() {}));

        intConsumer.accept(intResult);

        MatrixSupplier matrixSupplier = new MatrixSupplier(4);
        MatrixPrinter printer = new MatrixPrinter();

        Matrix A = matrixSupplier.get();
        Matrix B = matrixSupplier.get();

        System.out.println("\nMatrix A:");
        printer.accept(A);

        System.out.println("Matrix B:");
        printer.accept(B);

        MatrixService matrixService = new MatrixService(4);
        ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();


        // 🔹 ADD
        Matrix add = matrixService.execute(A, B, new MatrixAddOperation(cache), "ADD");
        System.out.println("Addition:");
        printer.accept(add);

        // 🔹 MULTIPLY
        Matrix mul = matrixService.execute(A, B, new MatrixMultiplyOperation(), "MULTIPLY");
        System.out.println("Multiplication:");
        printer.accept(mul);

        // 🔹 DIVIDE
        Matrix divideM = matrixService.execute(A, B, new MatrixDivideOperation(), "DIVIDE");
        System.out.println("Division:");
        printer.accept(divideM);

        matrixService.shutdown();

        System.out.println("\nFinal Results:");
        System.out.println("Unary Int: " + intResult);
        System.out.println("Binary: " + binaryResult);
        System.out.println("Boolean: " + booleanResult);
        System.out.println("BigInteger: " + bigResult);
    }
}