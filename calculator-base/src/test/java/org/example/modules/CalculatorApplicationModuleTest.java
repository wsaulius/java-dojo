package org.example.modules;

import org.example.enums.*;
import org.example.interfaces.*;
import org.example.interfaces.annotations.CalcPool;
import org.example.interfaces.annotations.MatrixPool;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.services.BinarySelector;
import org.example.factories.CalculationConsumerResolver;
import org.example.services.CalculatorService;
import org.example.services.UnaryBigIntegerSelector;
import org.example.services.UnaryBooleanSelector;
import org.example.services.UnaryDoubleSelector;
import org.example.services.UnaryIntSelector;
import org.example.services.UnaryLongSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

class CalculatorApplicationModuleTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new CalculatorApplicationModule());
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveServices() {
        assertNotNull(injector.getInstance(CalculatorService.class));
    }

    @Test
    void shouldResolveSelectors() {
        assertNotNull(injector.getInstance(BinarySelector.class));
        assertNotNull(injector.getInstance(UnaryIntSelector.class));
        assertNotNull(injector.getInstance(UnaryDoubleSelector.class));
        assertNotNull(injector.getInstance(UnaryLongSelector.class));
        assertNotNull(injector.getInstance(UnaryBooleanSelector.class));
        assertNotNull(injector.getInstance(UnaryBigIntegerSelector.class));
    }

    @Test
    void shouldResolveExecutionLayer() {
        assertNotNull(injector.getInstance(CalculationExecutor.class));
        assertNotNull(injector.getInstance(AsyncCalculationExecutor.class));
        assertNotNull(injector.getInstance(
                Key.get(ThreadPoolExecutor.class, MatrixPool.class)
        ));
        assertNotNull(injector.getInstance(
                Key.get(ThreadPoolExecutor.class, CalcPool.class)
        ));
    }

    @Test
    void shouldResolveResolvers() {
        assertNotNull(injector.getInstance(CalculationConsumerResolver.class));
    }

    @Test
    void shouldResolveTypedConsumers() {
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