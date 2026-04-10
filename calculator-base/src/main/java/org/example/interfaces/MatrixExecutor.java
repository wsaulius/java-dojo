package org.example.interfaces;

import org.example.enums.BinaryType;
import org.example.models.Matrix;

public interface MatrixExecutor {

    Matrix execute(Matrix A, Matrix B, BinaryType type, String operationName);

    void shutdown();
}