package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryLongType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.IntToLongFunction;

import static org.junit.jupiter.api.Assertions.*;

class UnaryLongOperationModuleTest {

    private Injector injector;
    private Map<UnaryLongType, IntToLongFunction> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new UnaryLongOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<UnaryLongType, IntToLongFunction>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllUnaryLongOperations() {
        assertEquals(1, operations.size());
        assertNotNull(operations.get(UnaryLongType.FACTORIAL));
    }

    @Test
    void shouldExecuteBoundUnaryLongOperationsCorrectly() {
        assertEquals(120L, operations.get(UnaryLongType.FACTORIAL).applyAsLong(5));
    }
}