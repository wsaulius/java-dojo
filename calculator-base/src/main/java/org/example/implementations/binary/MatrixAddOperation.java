package org.example.implementations.binary;

import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

public class MatrixAddOperation implements MatrixOperation {

    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {
        return A.get(row, col) + B.get(row, col);
    }
}