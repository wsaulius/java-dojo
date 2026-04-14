package org.example.implementations.binary;

import org.example.enums.BinaryType;
import org.example.models.Matrix;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MatrixBinaryOperationTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = mock(CalculatorService.class);
    }

    @Test
    void applyShouldDelegateUsingMatrixCellValues() {
        Matrix a = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });
        Matrix b = new Matrix(new int[][]{
                {5, 6},
                {7, 8}
        });

        when(calculatorService.runBinary(BinaryType.ADD, 4.0, 8.0)).thenReturn(12.0);

        MatrixBinaryOperation operation = new MatrixBinaryOperation(calculatorService, BinaryType.ADD);

        int result = operation.apply(a, b, 1, 1);

        assertEquals(12, result);
        verify(calculatorService).runBinary(BinaryType.ADD, 4.0, 8.0);
    }

    @Test
    void applyShouldConvertReturnedDoubleToInt() {
        Matrix a = new Matrix(new int[][]{
                {2}
        });
        Matrix b = new Matrix(new int[][]{
                {3}
        });

        when(calculatorService.runBinary(BinaryType.DIVIDE, 2.0, 3.0)).thenReturn(7.9);

        MatrixBinaryOperation operation = new MatrixBinaryOperation(calculatorService, BinaryType.DIVIDE);

        int result = operation.apply(a, b, 0, 0);

        assertEquals(7, result);
        verify(calculatorService).runBinary(BinaryType.DIVIDE, 2.0, 3.0);
    }

    @Test
    void applyShouldUseCacheForSameOperandsAndType() {
        Matrix a = new Matrix(new int[][]{
                {2}
        });
        Matrix b = new Matrix(new int[][]{
                {3}
        });

        when(calculatorService.runBinary(BinaryType.MULTIPLY, 2.0, 3.0)).thenReturn(6.0);

        MatrixBinaryOperation operation = new MatrixBinaryOperation(calculatorService, BinaryType.MULTIPLY);

        int first = operation.apply(a, b, 0, 0);
        int second = operation.apply(a, b, 0, 0);

        assertEquals(6, first);
        assertEquals(6, second);
        verify(calculatorService, times(1)).runBinary(BinaryType.MULTIPLY, 2.0, 3.0);
    }

    @Test
    void applyShouldNotUseCacheForDifferentOperands() {
        Matrix a = new Matrix(new int[][]{
                {2, 4}
        });
        Matrix b = new Matrix(new int[][]{
                {3, 5}
        });

        when(calculatorService.runBinary(BinaryType.ADD, 2.0, 3.0)).thenReturn(5.0);
        when(calculatorService.runBinary(BinaryType.ADD, 4.0, 5.0)).thenReturn(9.0);

        MatrixBinaryOperation operation = new MatrixBinaryOperation(calculatorService, BinaryType.ADD);

        int first = operation.apply(a, b, 0, 0);
        int second = operation.apply(a, b, 0, 1);

        assertEquals(5, first);
        assertEquals(9, second);
        verify(calculatorService).runBinary(BinaryType.ADD, 2.0, 3.0);
        verify(calculatorService).runBinary(BinaryType.ADD, 4.0, 5.0);
    }

    @Test
    void applyShouldNotShareCacheAcrossDifferentOperationTypes() {
        Matrix a = new Matrix(new int[][]{
                {2}
        });
        Matrix b = new Matrix(new int[][]{
                {3}
        });

        when(calculatorService.runBinary(BinaryType.ADD, 2.0, 3.0)).thenReturn(5.0);
        when(calculatorService.runBinary(BinaryType.MULTIPLY, 2.0, 3.0)).thenReturn(6.0);

        MatrixBinaryOperation addOperation = new MatrixBinaryOperation(calculatorService, BinaryType.ADD);
        MatrixBinaryOperation multiplyOperation = new MatrixBinaryOperation(calculatorService, BinaryType.MULTIPLY);

        int addResult = addOperation.apply(a, b, 0, 0);
        int multiplyResult = multiplyOperation.apply(a, b, 0, 0);

        assertEquals(5, addResult);
        assertEquals(6, multiplyResult);
        verify(calculatorService).runBinary(BinaryType.ADD, 2.0, 3.0);
        verify(calculatorService).runBinary(BinaryType.MULTIPLY, 2.0, 3.0);
    }
}