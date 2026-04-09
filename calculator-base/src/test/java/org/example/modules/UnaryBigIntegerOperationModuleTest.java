package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryBigIntegerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class UnaryBigIntegerOperationModuleTest {

    private Injector injector;
    private Map<UnaryBigIntegerType, Function<Integer, BigInteger>> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new UnaryBigIntegerOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<UnaryBigIntegerType, Function<Integer, BigInteger>>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllUnaryBigIntegerOperations() {
        assertEquals(1, operations.size());
        assertNotNull(operations.get(UnaryBigIntegerType.FIBONACCI));
    }

    @Test
    void shouldExecuteBoundUnaryBigIntegerOperationsCorrectly() {
        assertEquals(BigInteger.valueOf(55), operations.get(UnaryBigIntegerType.FIBONACCI).apply(10));
    }
}