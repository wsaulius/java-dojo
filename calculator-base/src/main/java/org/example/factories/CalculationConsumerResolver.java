package org.example.factories;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import jakarta.inject.Inject;
import org.example.enums.*;
import org.example.interfaces.CalculationConsumer;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;

import java.math.BigInteger;

public class CalculationConsumerResolver {

    private final Injector injector;

    @Inject
    public CalculationConsumerResolver(Injector injector) {
        this.injector = injector;
    }

    public CalculationConsumer<BinaryCalculationRecord> binary() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<BinaryCalculationRecord>>() {}
        ));
    }

    public CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> unaryInt() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {}
        ));
    }

    public CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Double, Double>> unaryDouble() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Double, Double>>>() {}
        ));
    }

    public CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Long, Long>> unaryLong() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Long, Long>>>() {}
        ));
    }

    public CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> unaryBoolean() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {}
        ));
    }

    public CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> unaryBigInteger() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {}
        ));
    }
}