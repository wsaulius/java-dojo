package org.example.matrix;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.enums.BinaryType;
import org.example.models.Matrix;
import org.example.execution.MatrixExecutor;
import org.example.modules.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixBinaryOperationTest {

    private Matrix A;
    private Matrix B;
    private MatrixExecutor matrixExecutor;

    @BeforeEach
    void setUp() {
        A = new Matrix(new int[][]{
                {7, 5, 8, 4},
                {6, 9, 2, 9},
                {4, 7, 5, 10},
                {10, 9, 1, 10}
        });

        B = new Matrix(new int[][]{
                {3, 3, 7, 7},
                {1, 4, 7, 3},
                {8, 2, 4, 4},
                {7, 1, 2, 7}
        });

        Injector injector = Guice.createInjector(
                new UnaryIntOperationModule(),
                new UnaryDoubleOperationModule(),
                new UnaryLongOperationModule(),
                new UnaryBooleanOperationModule(),
                new BinaryOperationModule(),
                new SequenceModule(),
                new SelectorModule(),
                new CalculatorConsumerModule(),
                new UnaryBigIntegerOperationModule(),
                new ExecutorModule()
        );

        matrixExecutor = injector.getInstance(MatrixExecutor.class);
    }


    @Test
    void testEntireMatrixAddition() {
        int[][] expected = {
                {10, 8, 15, 11},
                {7, 13, 9, 12},
                {12, 9, 9, 14},
                {17, 10, 3, 17}
        };

        Matrix add = matrixExecutor.execute(A, B, BinaryType.ADD, "ADD");
        assertTrue(Arrays.deepEquals(expected, add.data()), "Matrix addition result is incorrect");
    }

    @Test
    void testEntireMatrixMultiplication() {
        int[][] expected = {
                {118, 61, 124, 124},
                {106, 67, 131, 140},
                {129, 60, 117, 139},
                {117, 78, 157, 171}
        };

        Matrix mul = matrixExecutor.execute(A, B, BinaryType.MULTIPLY, "MULTIPLY");
        assertTrue(Arrays.deepEquals(expected, mul.data()), "Matrix multiplication result is incorrect");
    }

    @Test
    void testEntireMatrixSubtraction() {
        int[][] expected = {
                {4, 2, 1, -3},
                {5, 5, -5, 6},
                {-4, 5, 1, 6},
                {3, 8, -1, 3}
        };
        Matrix sub = matrixExecutor.execute(A, B, BinaryType.SUBTRACT, "SUBTRACT");
        assertTrue(Arrays.deepEquals(expected, sub.data()), "Matrix subtraction result is incorrect");
    }
}