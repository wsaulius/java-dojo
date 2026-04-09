package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.consumers.MatrixPrinter;
import org.example.enums.*;
import org.example.factories.CalculationConsumerResolver;
import org.example.implementations.binary.MatrixAddOperation;
import org.example.implementations.binary.MatrixDivideOperation;
import org.example.implementations.binary.MatrixMultiplyOperation;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.*;
import org.example.services.CalculatorService;
import org.example.services.MatrixService;
import org.example.suppliers.MatrixSupplier;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;


public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CalculatorApplicationModule());

        CalculatorService calculator = injector.getInstance(CalculatorService.class);
        CalculationConsumerResolver resolver = injector.getInstance(CalculationConsumerResolver.class);

        int intResult = calculator.runUnaryInt(UnaryIntType.CUBE, 5);
        resolver.unaryInt().accept(new UnaryCalculationRecord<>(UnaryIntType.CUBE, 5, intResult));

        double binaryResult = calculator.runBinary(BinaryType.ADD, 10.0, 20.0);
        resolver.binary().accept(new BinaryCalculationRecord(BinaryType.ADD, 10.0, 20.0, binaryResult));

        boolean booleanResult = calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17);
        resolver.unaryBoolean().accept(new UnaryCalculationRecord<>(UnaryBooleanType.IS_PRIME, 17, booleanResult));

        BigInteger bigIntegerResult = calculator.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 500);
        resolver.unaryBigInteger().accept(new UnaryCalculationRecord<>(UnaryBigIntegerType.FIBONACCI, 500, bigIntegerResult));

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
    }
}