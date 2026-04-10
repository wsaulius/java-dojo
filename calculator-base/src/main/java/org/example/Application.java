package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.consumers.MatrixPrinter;
import org.example.enums.*;
import org.example.execution.DefaultMatrixExecutor;
import org.example.factories.CalculationConsumerResolver;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.*;
import org.example.services.CalculatorService;
import org.example.suppliers.MatrixSupplier;

import java.math.BigInteger;

/**
 * Application entry point. Bootstraps the Guice injector and starts execution.
 */
public class Application {

    /**
     * Initializes dependency injection and runs the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CalculatorApplicationModule());

//        CalculatorService calculator = injector.getInstance(CalculatorService.class);
//        CalculationConsumerResolver resolver = injector.getInstance(CalculationConsumerResolver.class);
//        DefaultMatrixExecutor matrixExecutor = injector.getInstance(DefaultMatrixExecutor.class);
//
//        int intResult = calculator.runUnaryInt(UnaryIntType.CUBE, 5);
//        resolver.unaryInt().accept(new UnaryCalculationRecord<>(UnaryIntType.CUBE, 5, intResult));
//
//        double binaryResult = calculator.runBinary(BinaryType.ADD, 10.0, 20.0);
//        resolver.binary().accept(new BinaryCalculationRecord(BinaryType.ADD, 10.0, 20.0, binaryResult));
//
//        boolean booleanResult = calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17);
//        resolver.unaryBoolean().accept(new UnaryCalculationRecord<>(UnaryBooleanType.IS_PRIME, 17, booleanResult));
//
//        BigInteger bigIntegerResult = calculator.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 500);
//        resolver.unaryBigInteger().accept(new UnaryCalculationRecord<>(UnaryBigIntegerType.FIBONACCI, 500, bigIntegerResult));
//
//        MatrixSupplier matrixSupplier = new MatrixSupplier(10);
//        MatrixPrinter printer = new MatrixPrinter();
//
//        Matrix A = matrixSupplier.get();
//        Matrix B = matrixSupplier.get();
//
//        System.out.println("\nMatrix A:");
//        printer.accept(A);
//
//        System.out.println("Matrix B:");
//        printer.accept(B);
//
//        Matrix add = matrixExecutor.execute(A, B, BinaryType.ADD, "ADD");
//        System.out.println("Addition:");
//        printer.accept(add);
//
//        Matrix mul = matrixExecutor.execute(A, B, BinaryType.MULTIPLY, "MULTIPLY");
//        System.out.println("Multiplication:");
//        printer.accept(mul);
//
//        Matrix sub = matrixExecutor.execute(A, B, BinaryType.SUBTRACT, "SUBTRACT");
//        System.out.println("Subtraction:");
//        printer.accept(sub);
//
//        matrixExecutor.shutdown();
    }
}