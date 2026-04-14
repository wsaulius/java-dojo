package org.example.services;

import org.example.enums.UnaryBooleanType;
import org.example.implementations.unary.PrimeCheckOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnaryBooleanSelectorTest {

    private UnaryBooleanSelector selector;

    @BeforeEach
    void setUp() {
        selector = new UnaryBooleanSelector(Map.of(
                UnaryBooleanType.IS_PRIME, new PrimeCheckOperation()
        ));
    }

    @ParameterizedTest
    @MethodSource("unaryBooleanMappings")
    void shouldReturnCorrectImplementationForUnaryBooleanType(UnaryBooleanType type, Class<?> expectedClass) {
        var operation = selector.get(type);

        assertEquals(expectedClass, operation.getClass());
    }

    @ParameterizedTest
    @MethodSource("unaryBooleanMappings")
    void shouldReturnSameInstanceForSameUnaryBooleanType(UnaryBooleanType type, Class<?> ignored) {
        var first = selector.get(type);
        var second = selector.get(type);

        assertSame(first, second);
    }

    @Test
    void shouldThrowWhenNoMappingExists() {
        UnaryBooleanSelector selector = new UnaryBooleanSelector(Map.of());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> selector.get(UnaryBooleanType.IS_PRIME)
        );

        assertEquals(
                "No unary boolean operation registered for: IS_PRIME",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowForNullType() {
        assertThrows(NullPointerException.class, () -> selector.get(null));
    }

    private static Stream<Arguments> unaryBooleanMappings() {
        return Stream.of(
                Arguments.of(UnaryBooleanType.IS_PRIME, PrimeCheckOperation.class)
        );
    }
}