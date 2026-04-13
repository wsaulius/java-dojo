package org.example.interfaces;

import org.example.enums.BinaryType;
import org.example.models.Matrix;

import java.util.concurrent.CompletableFuture;

/**
 * Defines asynchronous execution of matrix operations using a binary operation type.
 */
public interface AsyncMatrixExecutor {

    /**
     * Submits a matrix operation for asynchronous execution.
     *
     * @param a first input matrix
     * @param b second input matrix
     * @param type operation type to apply
     * @param operationName operation name used for identification or logging
     * @return a CompletableFuture that will contain the resulting matrix once computation completes
     */
    CompletableFuture<Matrix> submit(Matrix a, Matrix b, BinaryType type, String operationName);

    /**
     * Shuts down the underlying execution resources.
     */
    void shutdown();
}