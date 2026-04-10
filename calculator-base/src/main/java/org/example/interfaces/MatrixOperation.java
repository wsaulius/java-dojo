package org.example.interfaces;

import org.example.models.Matrix;

/**
 * Represents a matrix cell computation for a specific row and column.
 */
@FunctionalInterface
public interface MatrixOperation {

    /**
     * Computes the value for a matrix cell.
     *
     * @param A first input matrix
     * @param B second input matrix
     * @param row target row index
     * @param col target column index
     * @return computed cell value
     */
    int apply(Matrix A, Matrix B, int row, int col);
}