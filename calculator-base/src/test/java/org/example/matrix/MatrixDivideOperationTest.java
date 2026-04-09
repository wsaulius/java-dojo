package org.example.matrix;

import org.example.implementations.binary.MatrixDivideOperation;
import org.example.models.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixDivideOperationTest {

    private Matrix A;
    private Matrix B;
    private MatrixDivideOperation operation;

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

        operation = new MatrixDivideOperation();
    }

    @Test
    void testSingleCellComputation() {
        // C[0][0] = 8
        int result = operation.apply(A, B, 0, 0);
        assertEquals(8, result);
    }

    @Test
    void testEntireMatrixDivision() {
        int[][] expected = {
                {8, 11, 5, 4},
                {12, 14, 5, 4},
                {9, 14, 7, 4},
                {13, 15, 7, 5}
        };

        for (int i = 0; i < A.rows(); i++) {
            for (int j = 0; j < B.cols(); j++) {
                int result = operation.apply(A, B, i, j);
                assertEquals(expected[i][j], result,
                        "Mismatch at (" + i + "," + j + ")");
            }
        }
    }

    @Test
    void testDivisionByZeroThrowsException() {
        Matrix B_zero = new Matrix(new int[][]{
                {0, 3, 7, 7}, // <- zero here
                {1, 4, 7, 3},
                {8, 2, 4, 4},
                {7, 1, 2, 7}
        });

        assertThrows(ArithmeticException.class, () -> {
            operation.apply(A, B_zero, 0, 0);
        });
    }
}