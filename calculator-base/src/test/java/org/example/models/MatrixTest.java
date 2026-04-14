package org.example.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void rowsAndColsShouldReturnCorrectDimensions() {
        Matrix matrix = new Matrix(new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        });

        assertEquals(2, matrix.rows());
        assertEquals(3, matrix.cols());
    }

    @Test
    void getShouldReturnCorrectValues() {
        Matrix matrix = new Matrix(new int[][]{
                {7, 8},
                {9, 10}
        });

        assertEquals(7, matrix.get(0, 0));
        assertEquals(8, matrix.get(0, 1));
        assertEquals(9, matrix.get(1, 0));
        assertEquals(10, matrix.get(1, 1));
    }

    @Test
    void setShouldUpdateValue() {
        Matrix matrix = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });

        matrix.set(1, 0, 99);

        assertEquals(99, matrix.get(1, 0));
    }

    @Test
    void setShouldMutateUnderlyingArray() {
        int[][] data = {
                {1, 2},
                {3, 4}
        };

        Matrix matrix = new Matrix(data);

        matrix.set(0, 1, 42);

        assertEquals(42, data[0][1]);
    }

    @Test
    void externalArrayMutationShouldBeVisible() {
        int[][] data = {
                {1, 2},
                {3, 4}
        };

        Matrix matrix = new Matrix(data);

        data[1][1] = 77;

        assertEquals(77, matrix.get(1, 1));
    }
}