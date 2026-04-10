package org.example.implementations.binary;

import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;
import org.example.services.CalculatorService;
import org.example.enums.BinaryType;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Applies a binary calculator operation to corresponding elements of two matrices.
 */
public class MatrixBinaryOperation implements MatrixOperation {

    private final CalculatorService calculatorService;
    private final BinaryType type;
    private final ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();

    /**
     * Creates a matrix operation for the given binary calculation type.
     *
     * @param calculatorService calculator service used to execute binary operations
     * @param type binary operation type applied to matrix elements
     */
    public MatrixBinaryOperation(CalculatorService calculatorService, BinaryType type) {
        this.calculatorService = calculatorService;
        this.type = type;
    }

    /** {@inheritDoc} */
    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {
        int left = A.get(row, col);
        int right = B.get(row, col);
        String key = left + type.name() + right;
        return cache.computeIfAbsent(key, k -> calculatorService.runBinary(type, left * 1.0, right * 1.0).intValue());
    }
}