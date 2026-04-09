package org.example.services;

import org.example.enums.BinaryType;
import org.example.enums.UnaryDoubleType;
import org.example.implementations.binary.*;
import org.example.implementations.unary.ExpOperation;
import org.example.implementations.unary.LogOperation;
import org.example.implementations.unary.SqrtOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnaryDoubleSelectorTest {

    private UnaryDoubleSelector selector;

    @BeforeEach
    void setUp() {
        selector = new UnaryDoubleSelector(Map.of(
                UnaryDoubleType.SQRT, new SqrtOperation(),
                UnaryDoubleType.LOG, new LogOperation(),
                UnaryDoubleType.EXP, new ExpOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("unaryDoubleMappings")
    void shouldReturnCorrectImplementationForUnaryDoubleType(UnaryDoubleType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("unaryDoubleMappings")
    void shouldReturnSameInstanceForSameUnaryDoubleType(UnaryDoubleType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> unaryDoubleMappings() {
        return Stream.of(
                Arguments.of(UnaryDoubleType.SQRT, SqrtOperation.class),
                Arguments.of(UnaryDoubleType.LOG, LogOperation.class),
                Arguments.of(UnaryDoubleType.EXP, ExpOperation.class)
        );
    }
}