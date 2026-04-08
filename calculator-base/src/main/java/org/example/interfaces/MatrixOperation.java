package org.example.interfaces;

import org.example.models.Matrix;

@FunctionalInterface
public interface MatrixOperation {
    int apply(Matrix A, Matrix B, int row, int col);
}