package org.example.services;

import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.implementations.unary.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UnaryIntSelectorTest {

    private UnaryIntSelector selector;

    @BeforeEach
    void setUp() {
        selector = new UnaryIntSelector(Map.of(
                UnaryIntType.ABS, new AbsOperation(),
                UnaryIntType.NEGATE, new NegateOperation(),
                UnaryIntType.SQUARE, new SquareOperation(),
                UnaryIntType.CUBE, new CubeOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("unaryIntMappings")
    void shouldReturnCorrectImplementationForUnaryIntType(UnaryIntType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("unaryIntMappings")
    void shouldReturnSameInstanceForSameUnaryIntType(UnaryIntType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> unaryIntMappings() {
        return Stream.of(
                Arguments.of(UnaryIntType.ABS, AbsOperation.class),
                Arguments.of(UnaryIntType.NEGATE, NegateOperation.class),
                Arguments.of(UnaryIntType.SQUARE, SquareOperation.class),
                Arguments.of(UnaryIntType.CUBE, CubeOperation.class)
        );
    }
}