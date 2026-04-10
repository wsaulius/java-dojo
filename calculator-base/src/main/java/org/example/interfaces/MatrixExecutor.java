package org.example.interfaces;

import org.example.enums.BinaryType;
import org.example.models.Matrix;

/**
 * Defines execution of matrix operations using a binary operation type.
 */
public interface MatrixExecutor {

    /**
     * Executes a matrix operation on two input matrices.
     *
     * @param A first input matrix
     * @param B second input matrix
     * @param type operation type to apply
     * @param operationName operation name used for identification or logging
     * @return resulting matrix produced by the operation
     */
    Matrix execute(Matrix A, Matrix B, BinaryType type, String operationName);

    /**
     * Shuts down the underlying execution resources.
     */
    void shutdown();
}