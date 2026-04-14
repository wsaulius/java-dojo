package org.example.suppliers;

import org.example.models.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixSupplierTest {

    @Test
    void getShouldCreateMatrixWithCorrectDimensions() {
        MatrixSupplier supplier = new MatrixSupplier(4);

        Matrix matrix = supplier.get();

        assertEquals(4, matrix.rows());
        assertEquals(4, matrix.cols());
    }

    @Test
    void getShouldFillMatrixWithValuesInRange() {
        MatrixSupplier supplier = new MatrixSupplier(5);

        Matrix matrix = supplier.get();

        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                int value = matrix.get(i, j);
                assertTrue(value >= 1 && value <= 10,
                        "Value out of range: " + value);
            }
        }
    }

    @Test
    void getShouldPopulateAllCells() {
        MatrixSupplier supplier = new MatrixSupplier(3);

        Matrix matrix = supplier.get();

        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                // Since range is 1–10, zero means uninitialized
                assertNotEquals(0, matrix.get(i, j));
            }
        }
    }

    @Test
    void getShouldReturnNewMatrixInstanceEachCall() {
        MatrixSupplier supplier = new MatrixSupplier(2);

        Matrix first = supplier.get();
        Matrix second = supplier.get();

        assertNotSame(first, second);
    }
}