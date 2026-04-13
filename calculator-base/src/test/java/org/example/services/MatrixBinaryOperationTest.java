package org.example.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.enums.BinaryType;
import org.example.execution.DefaultMatrixExecutor;
import org.example.models.Matrix;
import org.example.modules.CalculatorApplicationModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixBinaryOperationTest {

    private Matrix a;
    private Matrix b;
    private DefaultMatrixExecutor matrixExecutor;

    @BeforeEach
    void setUp() {
        a = new Matrix(new int[][]{
                {7, 5, 8, 4},
                {6, 9, 2, 9},
                {4, 7, 5, 10},
                {10, 9, 1, 10}
        });

        b = new Matrix(new int[][]{
                {3, 3, 7, 7},
                {1, 4, 7, 3},
                {8, 2, 4, 4},
                {7, 1, 2, 7}
        });

        Injector injector = Guice.createInjector(new CalculatorApplicationModule());
        matrixExecutor = injector.getInstance(DefaultMatrixExecutor.class);
    }

    @Test
    void testEntireMatrixAddition() throws Exception {
        int[][] expected = {
                {10, 8, 15, 11},
                {7, 13, 9, 12},
                {12, 9, 9, 14},
                {17, 10, 3, 17}
        };

        Future<Matrix> future = matrixExecutor.execute(a, b, BinaryType.ADD, "ADD");
        Matrix add = future.get();

        assertTrue(Arrays.deepEquals(expected, add.data()), "Matrix addition result is incorrect");
    }

    @Test
    void testEntireMatrixMultiplication() throws Exception {
        int[][] expected = {
                {118, 61, 124, 124},
                {106, 67, 131, 140},
                {129, 60, 117, 139},
                {117, 78, 157, 171}
        };

        Future<Matrix> future = matrixExecutor.execute(a, b, BinaryType.MULTIPLY, "MULTIPLY");
        Matrix mul = future.get();

        assertTrue(Arrays.deepEquals(expected, mul.data()), "Matrix multiplication result is incorrect");
    }

    @Test
    void testEntireMatrixSubtraction() throws Exception {
        int[][] expected = {
                {4, 2, 1, -3},
                {5, 5, -5, 6},
                {-4, 5, 1, 6},
                {3, 8, -1, 3}
        };

        Future<Matrix> future = matrixExecutor.execute(a, b, BinaryType.SUBTRACT, "SUBTRACT");
        Matrix sub = future.get();

        assertTrue(Arrays.deepEquals(expected, sub.data()), "Matrix subtraction result is incorrect");
    }
}