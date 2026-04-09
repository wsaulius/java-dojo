package org.example.matrix;

import org.example.models.Matrix;
import org.example.services.MatrixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

//class MatrixDivideOperationTest {
//
//    static private Matrix A;
//    static private Matrix B;
//    static private MatrixDivideOperation operation;
//    static private MatrixService matrixService;
//
//    @BeforeEach
//    void setUp() {
//        A = new Matrix(new int[][]{
//                {7, 5, 8, 4},
//                {6, 9, 2, 9},
//                {4, 7, 5, 10},
//                {10, 9, 1, 10}
//        });
//
//        B = new Matrix(new int[][]{
//                {3, 3, 7, 7},
//                {1, 4, 7, 3},
//                {8, 2, 4, 4},
//                {7, 1, 2, 7}
//        });
//        operation = new MatrixDivideOperation();
//        matrixService = new MatrixService(4);
//
//    }
//
//    @Test
//    void testSingleCellComputation() {
//        // C[0][0] = 8
//        int result = operation.apply(A, B, 0, 0);
//        assertEquals(8, result);
//    }
//
//    @Test
//    void testEntireMatrixDivision() {
//        int[][] expected = {
//                {8, 11, 5, 4},
//                {12, 14, 5, 4},
//                {9, 14, 7, 4},
//                {13, 15, 7, 5}
//        };
//
//        for (int i = 0; i < A.rows(); i++) {
//            for (int j = 0; j < B.cols(); j++) {
//                int result = operation.apply(A, B, i, j);
//                assertEquals(expected[i][j], result,
//                        "Mismatch at (" + i + "," + j + ")");
//            }
//        }
//    }
//
//    @Test
//    void testDivisionByZeroThrowsException() {
//        B = new Matrix(new int[][]{
//                {0, 3, 7, 7}, // <- zero here
//                {1, 4, 7, 3},
//                {8, 2, 4, 4},
//                {7, 1, 2, 7}
//        });
//
//        assertThrows(ArithmeticException.class, () -> {
//            operation.apply(A, B, 0, 0);
//        });
//    }
//
//    @Test
//    void testEntireMatrixMultiplication() {
//        int[][] expected = {
//                {118, 61, 124, 124},
//                {106, 67, 131, 140},
//                {129, 60, 117, 139},
//                {117, 78, 157, 171}
//        };
//
//        Matrix mul = matrixService.execute(A, B, new MatrixMultiplyOperation(), "MULTIPLY");
//        assertTrue(Arrays.deepEquals(expected, mul.data()), "Matrix multiplication result is incorrect");
//    }
//}