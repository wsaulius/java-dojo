package org.example.modules;

import com.google.inject.AbstractModule;
import org.example.consumers.CalculationPrinter;
import org.example.consumers.ResultPrinter;
import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.ResultConsumer;

import com.google.inject.TypeLiteral;
import org.example.enums.UnaryBigIntegerType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;

import java.math.BigInteger;

public class CalculatorConsumerModule extends AbstractModule {

    @Override
    protected void configure() {
        bindResultConsumer(new TypeLiteral<ResultConsumer<Integer>>() {}, new TypeLiteral<ResultPrinter<Integer>>() {});
        bindResultConsumer(new TypeLiteral<ResultConsumer<Double>>() {}, new TypeLiteral<ResultPrinter<Double>>() {});
        bindResultConsumer(new TypeLiteral<ResultConsumer<Long>>() {}, new TypeLiteral<ResultPrinter<Long>>() {});
        bindResultConsumer(new TypeLiteral<ResultConsumer<Boolean>>() {}, new TypeLiteral<ResultPrinter<Boolean>>() {});

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<BinaryCalculationRecord>>() {},
                new TypeLiteral<CalculationPrinter<BinaryCalculationRecord>>() {}
        );

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {},
                new TypeLiteral<CalculationPrinter<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {}
        );

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Double, Double>>>() {},
                new TypeLiteral<CalculationPrinter<UnaryCalculationRecord<UnaryDoubleType, Double, Double>>>() {}
        );

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Long, Long>>>() {},
                new TypeLiteral<CalculationPrinter<UnaryCalculationRecord<UnaryLongType, Long, Long>>>() {}
        );

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {},
                new TypeLiteral<CalculationPrinter<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {}
        );

        bindCalculationConsumer(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {},
                new TypeLiteral<CalculationPrinter<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {}
        );
    }

    private <T> void bindResultConsumer(
            TypeLiteral<ResultConsumer<T>> consumerType,
            TypeLiteral<? extends ResultConsumer<T>> implementationType
    ) {
        bind(consumerType).to(implementationType);
    }

    private <T> void bindCalculationConsumer(
            TypeLiteral<CalculationConsumer<T>> consumerType,
            TypeLiteral<? extends CalculationConsumer<T>> implementationType
    ) {
        bind(consumerType).to(implementationType);
    }
}