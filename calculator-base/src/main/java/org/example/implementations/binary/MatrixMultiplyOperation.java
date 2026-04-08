package org.example.implementations.binary;

import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

public class MatrixMultiplyOperation implements MatrixOperation {

    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {

        int sum = 0;

        for (int k = 0; k < A.cols(); k++) {
            sum += A.get(row, k) * B.get(k, col);
        }

        return sum;
    }
}