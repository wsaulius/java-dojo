package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.enums.BinaryType;
import org.example.implementations.binary.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.DoubleBinaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class BinaryOperationModuleTest {

    private Injector injector;
    private Map<BinaryType, DoubleBinaryOperator> operations;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new BinaryOperationModule());
        operations = injector.getInstance(Key.get(new TypeLiteral<Map<BinaryType, DoubleBinaryOperator>>() {}));
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllBinaryOperations() {
        assertEquals(BinaryType.values().length, operations.size());
        assertNotNull(operations.get(BinaryType.ADD));
        assertNotNull(operations.get(BinaryType.SUBTRACT));
        assertNotNull(operations.get(BinaryType.MULTIPLY));
        assertNotNull(operations.get(BinaryType.DIVIDE));
        assertNotNull(operations.get(BinaryType.MODULO));
        assertNotNull(operations.get(BinaryType.POWER));
        assertNotNull(operations.get(BinaryType.MAX));
        assertNotNull(operations.get(BinaryType.MIN));
    }

    @Test
    void shouldExecuteBoundBinaryOperationsCorrectly() {
        assertEquals(5.0, operations.get(BinaryType.ADD).applyAsDouble(2, 3));
        assertEquals(1.0, operations.get(BinaryType.SUBTRACT).applyAsDouble(3, 2));
        assertEquals(6.0, operations.get(BinaryType.MULTIPLY).applyAsDouble(2, 3));
        assertEquals(2.0, operations.get(BinaryType.DIVIDE).applyAsDouble(6, 3));
        assertEquals(1.0, operations.get(BinaryType.MODULO).applyAsDouble(7, 3));
        assertEquals(8.0, operations.get(BinaryType.POWER).applyAsDouble(2, 3));
        assertEquals(7.0, operations.get(BinaryType.MAX).applyAsDouble(7, 3));
        assertEquals(3.0, operations.get(BinaryType.MIN).applyAsDouble(7, 3));
    }
}