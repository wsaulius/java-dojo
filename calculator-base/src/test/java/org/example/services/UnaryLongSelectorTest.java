package org.example.services;

import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.implementations.unary.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnaryLongSelectorTest {

    private UnaryLongSelector selector;

    @BeforeEach
    void setUp() {
        selector = new UnaryLongSelector(Map.of(
                UnaryLongType.FACTORIAL, new FactorialOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("unaryLongMappings")
    void shouldReturnCorrectImplementationForUnaryLongType(UnaryLongType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("unaryLongMappings")
    void shouldReturnSameInstanceForSameUnaryLongType(UnaryLongType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> unaryLongMappings() {
        return Stream.of(
                Arguments.of(UnaryLongType.FACTORIAL, FactorialOperation.class)
        );
    }
}