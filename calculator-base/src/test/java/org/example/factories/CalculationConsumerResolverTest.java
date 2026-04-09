package org.example.factories;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.*;
import org.example.interfaces.CalculationConsumer;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CalculationConsumerResolverTest {

    @Test
    void shouldResolveBinaryConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<BinaryCalculationRecord> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<BinaryCalculationRecord>> key = Key.get(
                new TypeLiteral<CalculationConsumer<BinaryCalculationRecord>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<BinaryCalculationRecord> result = resolver.binary();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }

    @Test
    void shouldResolveUnaryIntConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> key = Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> result = resolver.unaryInt();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }

    @Test
    void shouldResolveUnaryDoubleConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> key = Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> result = resolver.unaryDouble();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }

    @Test
    void shouldResolveUnaryLongConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> key = Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>> result = resolver.unaryLong();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }

    @Test
    void shouldResolveUnaryBooleanConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> key = Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> result = resolver.unaryBoolean();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }

    @Test
    void shouldResolveUnaryBigIntegerConsumer() {
        Injector injector = mock(Injector.class);
        CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> consumer = mock(CalculationConsumer.class);

        Key<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> key = Key.get(
                new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {}
        );

        when(injector.getInstance(key)).thenReturn(consumer);

        CalculationConsumerResolver resolver = new CalculationConsumerResolver(injector);

        CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> result = resolver.unaryBigInteger();

        assertSame(consumer, result);
        verify(injector).getInstance(key);
    }
}