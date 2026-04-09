package org.example.services;

import org.example.enums.UnaryBigIntegerType;
import org.example.implementations.binary.*;
import org.example.implementations.unary.FibonacciOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnaryBigIntegerSelectorTest {

    private UnaryBigIntegerSelector selector;

    @BeforeEach
    void setUp() {
        selector = new UnaryBigIntegerSelector(Map.of(
                UnaryBigIntegerType.FIBONACCI, new FibonacciOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("unaryBigIntegerMappings")
    void shouldReturnCorrectImplementationForUnaryBigIntegerType(UnaryBigIntegerType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("unaryBigIntegerMappings")
    void shouldReturnSameInstanceForSameUnaryBigIntegerType(UnaryBigIntegerType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> unaryBigIntegerMappings() {
        return Stream.of(
                Arguments.of(UnaryBigIntegerType.FIBONACCI, FibonacciOperation.class)
        );
    }
}