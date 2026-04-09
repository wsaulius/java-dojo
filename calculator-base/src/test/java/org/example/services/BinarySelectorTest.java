package org.example.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.enums.BinaryType;
import org.example.implementations.binary.*;
import org.example.modules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BinarySelectorTest {

    private BinarySelector selector;

    @BeforeEach
    void setUp() {
        selector = new BinarySelector(Map.of(
                BinaryType.ADD, new AddOperation(),
                BinaryType.SUBTRACT, new SubtractOperation(),
                BinaryType.MULTIPLY, new MultiplyOperation(),
                BinaryType.DIVIDE, new DivideOperation(),
                BinaryType.MODULO, new ModuloOperation(),
                BinaryType.POWER, new PowerOperation(),
                BinaryType.MAX, new MaxOperation(),
                BinaryType.MIN, new MinOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("binaryMappings")
    void shouldReturnCorrectImplementationForBinaryType(BinaryType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("binaryMappings")
    void shouldReturnSameInstanceForSameBinaryType(BinaryType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> binaryMappings() {
        return Map.<BinaryType, Class<?>>of(
                BinaryType.ADD, AddOperation.class,
                BinaryType.SUBTRACT, SubtractOperation.class,
                BinaryType.MULTIPLY, MultiplyOperation.class,
                BinaryType.DIVIDE, DivideOperation.class,
                BinaryType.MODULO, ModuloOperation.class,
                BinaryType.POWER, PowerOperation.class,
                BinaryType.MAX, MaxOperation.class,
                BinaryType.MIN, MinOperation.class
        ).entrySet().stream().map(entry ->
                Arguments.of(entry.getKey(), entry.getValue())
        );
    }
}