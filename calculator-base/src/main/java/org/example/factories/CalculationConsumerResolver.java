package org.example.factories;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.enums.*;
import org.example.interfaces.CalculationConsumer;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;

import java.math.BigInteger;

/**
 * Resolves typed calculation consumer instances from the injector.
 */
@Singleton
public final class CalculationConsumerResolver {

    private final Injector injector;

    /**
     * Creates a resolver backed by the given injector.
     *
     * @param injector injector used to resolve typed consumer bindings
     */
    @Inject
    public CalculationConsumerResolver(Injector injector) {
        this.injector = injector;
    }

    /**
     * Returns the consumer for binary calculation records.
     *
     * @return binary calculation consumer
     */
    public CalculationConsumer<BinaryCalculationRecord> binary() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<BinaryCalculationRecord>>() {}
        ));
    }

    /**
     * Returns the consumer for unary integer calculation records.
     *
     * @return unary integer calculation consumer
     */
    public CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> unaryInt() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {}
        ));
    }

    /**
     * Returns the consumer for unary double calculation records.
     *
     * @return unary double calculation consumer
     */
    public CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> unaryDouble() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>>() {}
        ));
    }

    /**
     * Returns the consumer for unary long calculation records.
     *
     * @return unary long calculation consumer
     */
    public CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>> unaryLong() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>>>() {}
        ));
    }

    /**
     * Returns the consumer for unary boolean calculation records.
     *
     * @return unary boolean calculation consumer
     */
    public CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> unaryBoolean() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {}
        ));
    }

    /**
     * Returns the consumer for unary BigInteger calculation records.
     *
     * @return unary BigInteger calculation consumer
     */
    public CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> unaryBigInteger() {
        return injector.getInstance(Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {}
        ));
    }
}