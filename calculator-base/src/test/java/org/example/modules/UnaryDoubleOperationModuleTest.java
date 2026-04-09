package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryDoubleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.IntToDoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class UnaryDoubleOperationModuleTest {

    private Injector injector;
    private Map<UnaryDoubleType, IntToDoubleFunction> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new UnaryDoubleOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<UnaryDoubleType, IntToDoubleFunction>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllUnaryDoubleOperations() {
        assertEquals(3, operations.size());
        assertNotNull(operations.get(UnaryDoubleType.SQRT));
        assertNotNull(operations.get(UnaryDoubleType.LOG));
        assertNotNull(operations.get(UnaryDoubleType.EXP));
    }

    @Test
    void shouldExecuteBoundUnaryDoubleOperationsCorrectly() {
        assertEquals(4.0, operations.get(UnaryDoubleType.SQRT).applyAsDouble(16), 1e-9);
        assertEquals(Math.log(10), operations.get(UnaryDoubleType.LOG).applyAsDouble(10), 1e-9);
        assertEquals(Math.exp(2), operations.get(UnaryDoubleType.EXP).applyAsDouble(2), 1e-9);
    }
}