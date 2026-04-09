package org.example.services;

import org.example.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CalculatorServiceTest {

    private UnaryIntSelector unaryIntSelector;
    private UnaryDoubleSelector unaryDoubleSelector;
    private UnaryLongSelector unaryLongSelector;
    private UnaryBooleanSelector unaryBooleanSelector;
    private BinarySelector binarySelector;
    private UnaryBigIntegerSelector unaryBigIntegerSelector;

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        unaryIntSelector = mock(UnaryIntSelector.class);
        unaryDoubleSelector = mock(UnaryDoubleSelector.class);
        unaryLongSelector = mock(UnaryLongSelector.class);
        unaryBooleanSelector = mock(UnaryBooleanSelector.class);
        binarySelector = mock(BinarySelector.class);
        unaryBigIntegerSelector = mock(UnaryBigIntegerSelector.class);

        calculatorService = new CalculatorService(
                unaryIntSelector,
                unaryDoubleSelector,
                unaryLongSelector,
                unaryBooleanSelector,
                binarySelector,
                unaryBigIntegerSelector
        );
    }

    @Test
    void shouldRunUnaryIntOperation() {
        IntUnaryOperator operation = mock(IntUnaryOperator.class);
        when(unaryIntSelector.get(UnaryIntType.ABS)).thenReturn(operation);
        when(operation.applyAsInt(5)).thenReturn(5);

        Integer result = calculatorService.runUnaryInt(UnaryIntType.ABS, 5);

        assertEquals(5, result);
        verify(unaryIntSelector).get(UnaryIntType.ABS);
        verify(operation).applyAsInt(5);
    }

    @Test
    void shouldRunUnaryDoubleOperation() {
        IntToDoubleFunction operation = mock(IntToDoubleFunction.class);
        when(unaryDoubleSelector.get(UnaryDoubleType.SQRT)).thenReturn(operation);
        when(operation.applyAsDouble(16)).thenReturn(4.0);

        Double result = calculatorService.runUnaryDouble(UnaryDoubleType.SQRT, 16);

        assertEquals(4.0, result);
        verify(unaryDoubleSelector).get(UnaryDoubleType.SQRT);
        verify(operation).applyAsDouble(16);
    }

    @Test
    void shouldRunUnaryLongOperation() {
        IntToLongFunction operation = mock(IntToLongFunction.class);
        when(unaryLongSelector.get(UnaryLongType.FACTORIAL)).thenReturn(operation);
        when(operation.applyAsLong(5)).thenReturn(120L);

        Long result = calculatorService.runUnaryLong(UnaryLongType.FACTORIAL, 5);

        assertEquals(120L, result);
        verify(unaryLongSelector).get(UnaryLongType.FACTORIAL);
        verify(operation).applyAsLong(5);
    }

    @Test
    void shouldRunUnaryBooleanOperation() {
        IntPredicate operation = mock(IntPredicate.class);
        when(unaryBooleanSelector.get(UnaryBooleanType.IS_PRIME)).thenReturn(operation);
        when(operation.test(13)).thenReturn(true);

        Boolean result = calculatorService.runUnaryBoolean(UnaryBooleanType.IS_PRIME, 13);

        assertEquals(true, result);
        verify(unaryBooleanSelector).get(UnaryBooleanType.IS_PRIME);
        verify(operation).test(13);
    }

    @Test
    void shouldRunBinaryOperation() {
        DoubleBinaryOperator operation = mock(DoubleBinaryOperator.class);
        when(binarySelector.get(BinaryType.ADD)).thenReturn(operation);
        when(operation.applyAsDouble(5.0, 3.0)).thenReturn(8.0);

        Double result = calculatorService.runBinary(BinaryType.ADD, 5.0, 3.0);

        assertEquals(8.0, result);
        verify(binarySelector).get(BinaryType.ADD);
        verify(operation).applyAsDouble(5.0, 3.0);
    }

    @Test
    void shouldRunUnaryBigIntegerOperation() {
        @SuppressWarnings("unchecked")
        Function<Integer, BigInteger> operation = mock(Function.class);
        when(unaryBigIntegerSelector.get(UnaryBigIntegerType.FIBONACCI)).thenReturn(operation);
        when(operation.apply(10)).thenReturn(BigInteger.valueOf(55));

        BigInteger result = calculatorService.runUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 10);

        assertEquals(BigInteger.valueOf(55), result);
        verify(unaryBigIntegerSelector).get(UnaryBigIntegerType.FIBONACCI);
        verify(operation).apply(10);
    }

    @Test
    void shouldPropagateExceptionFromUnaryIntSelector() {
        when(unaryIntSelector.get(UnaryIntType.ABS))
                .thenThrow(new IllegalArgumentException("invalid type"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculatorService.runUnaryInt(UnaryIntType.ABS, 5)
        );

        assertEquals("invalid type", ex.getMessage());
    }

    @Test
    void shouldPropagateExceptionFromBinaryOperation() {
        DoubleBinaryOperator operation = mock(DoubleBinaryOperator.class);
        when(binarySelector.get(BinaryType.DIVIDE)).thenReturn(operation);
        when(operation.applyAsDouble(10.0, 0.0))
                .thenThrow(new ArithmeticException("Division by zero"));

        ArithmeticException ex = assertThrows(
                ArithmeticException.class,
                () -> calculatorService.runBinary(BinaryType.DIVIDE, 10.0, 0.0)
        );

        assertEquals("Division by zero", ex.getMessage());
    }

    @Test
    void shouldUseOnlyBinarySelectorForBinaryExecution() {
        DoubleBinaryOperator operation = mock(DoubleBinaryOperator.class);
        when(binarySelector.get(BinaryType.ADD)).thenReturn(operation);
        when(operation.applyAsDouble(1.0, 2.0)).thenReturn(3.0);

        calculatorService.runBinary(BinaryType.ADD, 1.0, 2.0);

        verify(binarySelector).get(BinaryType.ADD);
        verifyNoInteractions(
                unaryIntSelector,
                unaryDoubleSelector,
                unaryLongSelector,
                unaryBooleanSelector,
                unaryBigIntegerSelector
        );
    }
}