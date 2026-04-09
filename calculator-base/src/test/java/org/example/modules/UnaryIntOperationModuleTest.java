package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.UnaryIntType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class UnaryIntOperationModuleTest {

    private Injector injector;
    private Map<UnaryIntType, IntUnaryOperator> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new UnaryIntOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<UnaryIntType, IntUnaryOperator>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllUnaryIntOperations() {
        assertEquals(4, operations.size());
        assertNotNull(operations.get(UnaryIntType.SQUARE));
        assertNotNull(operations.get(UnaryIntType.CUBE));
        assertNotNull(operations.get(UnaryIntType.NEGATE));
        assertNotNull(operations.get(UnaryIntType.ABS));
    }

    @Test
    void shouldExecuteBoundUnaryIntOperationsCorrectly() {
        assertEquals(16, operations.get(UnaryIntType.SQUARE).applyAsInt(4));
        assertEquals(27, operations.get(UnaryIntType.CUBE).applyAsInt(3));
        assertEquals(-5, operations.get(UnaryIntType.NEGATE).applyAsInt(5));
        assertEquals(9, operations.get(UnaryIntType.ABS).applyAsInt(-9));
    }
}