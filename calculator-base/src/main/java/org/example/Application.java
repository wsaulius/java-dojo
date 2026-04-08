package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.enums.*;
import org.example.factories.CalculationConsumerResolver;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.*;
import org.example.services.CalculatorService;
import java.math.BigInteger;


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
        CalculationConsumerResolver resolver = injector.getInstance(CalculationConsumerResolver.class);

        int intResult = calculator.runUnaryInt(UnaryIntType.CUBE, 5);
        resolver.unaryInt().accept(new UnaryCalculationRecord<>(UnaryIntType.CUBE, 5, intResult));

        double binaryResult = calculator.runBinary(BinaryType.ADD, 10.0, 20.0);
        resolver.binary().accept(new BinaryCalculationRecord(BinaryType.ADD, 10.0, 20.0, binaryResult));

        boolean booleanResult = calculator.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 17);
        resolver.unaryBoolean().accept(new UnaryCalculationRecord<>(UnaryBooleanType.IS_PRIME, 17, booleanResult));

        BigInteger bigIntegerResult = calculator.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 500);
        resolver.unaryBigInteger().accept(new UnaryCalculationRecord<>(UnaryBigIntegerType.FIBONACCI, 500, bigIntegerResult));
    }
}