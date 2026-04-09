package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryBooleanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.*;

class UnaryBooleanOperationModuleTest {

    private Injector injector;
    private Map<UnaryBooleanType, IntPredicate> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new UnaryBooleanOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<UnaryBooleanType, IntPredicate>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllUnaryBooleanOperations() {
        assertEquals(1, operations.size());
        assertNotNull(operations.get(UnaryBooleanType.IS_PRIME));
    }

    @Test
    void shouldExecuteBoundUnaryBooleanOperationsCorrectly() {
        assertTrue(operations.get(UnaryBooleanType.IS_PRIME).test(13));
        assertFalse(operations.get(UnaryBooleanType.IS_PRIME).test(12));
    }
}