package org.example.implementations.binary;

import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class MatrixMultiplyOperation implements MatrixOperation {
    private final ConcurrentHashMap<String, Integer> cache;

    public MatrixMultiplyOperation(ConcurrentHashMap<String, Integer> cache) {
        this.cache = cache;
    }

    public MatrixMultiplyOperation() {
        this.cache = new ConcurrentHashMap<>();
    }
    // cached multiply for reuse
    private int cachedMultiply(int a, int b) {
        String key = a + "*" + b;

        return cache.computeIfAbsent(key, k -> {
            System.out.println("Computing new multiply: " + key); // optional log
            return a * b;
        });
    }

    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {
        return IntStream.range(0, A.cols())
                .map(k -> cachedMultiply(A.get(row, k), B.get(k, col)))
                .sum();
    }
}