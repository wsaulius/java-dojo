package org.example.implementations.binary;

import jakarta.inject.Singleton;
import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

import java.util.stream.IntStream;

@Singleton
public class MatrixDivideOperation implements MatrixOperation {

    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {
        return IntStream.range(0, A.cols())
                .map(k -> {
                    int divisor = B.get(k, col);
                    if (divisor == 0) throw new ArithmeticException("Division by zero");
                    return A.get(row, k) / divisor;
                })
                .sum();
    }
}