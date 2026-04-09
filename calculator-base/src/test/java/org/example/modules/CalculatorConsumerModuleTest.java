package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryBigIntegerType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.ResultConsumer;
import org.example.interfaces.SequenceConsumer;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorConsumerModuleTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new CalculatorConsumerModule());
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveTypedResultConsumers() {
        ResultConsumer<Integer> integerConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Integer>>() {}));

        ResultConsumer<Double> doubleConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Double>>() {}));

        ResultConsumer<Long> longConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Long>>() {}));

        ResultConsumer<Boolean> booleanConsumer =
                injector.getInstance(Key.get(new TypeLiteral<ResultConsumer<Boolean>>() {}));

        assertNotNull(integerConsumer);
        assertNotNull(doubleConsumer);
        assertNotNull(longConsumer);
        assertNotNull(booleanConsumer);
    }

    @Test
    void shouldResolveTypedCalculationConsumers() {
        CalculationConsumer<BinaryCalculationRecord> binary =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<BinaryCalculationRecord>>() {}));

        CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> unaryInt =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>>() {}));

        CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> unaryDouble =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>>() {}));

        CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>> unaryLong =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryLongType, Integer, Long>>>() {}));

        CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> unaryBoolean =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>>() {}));

        CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> unaryBigInt =
                injector.getInstance(Key.get(new TypeLiteral<CalculationConsumer<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>>() {}));

        assertNotNull(binary);
        assertNotNull(unaryInt);
        assertNotNull(unaryDouble);
        assertNotNull(unaryLong);
        assertNotNull(unaryBoolean);
        assertNotNull(unaryBigInt);
    }

}