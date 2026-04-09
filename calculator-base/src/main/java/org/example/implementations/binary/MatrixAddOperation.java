package org.example.implementations.binary;

import jakarta.inject.Singleton;
import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MatrixAddOperation implements MatrixOperation {

    private final ConcurrentHashMap<String, Integer> cache;

    public MatrixAddOperation(ConcurrentHashMap<String, Integer> cache) {
        this.cache = cache;
    }

    public MatrixAddOperation() {
        this.cache = new ConcurrentHashMap<>();
    }

    private int cachedAdd(int a, int b) {
        String key = Math.min(a, b) + "+" + Math.max(a, b);
        return cache.computeIfAbsent(key, k -> {
            System.out.println("Computing new value for: " + key); // first time
            return a + b;
        });
    }

    @Override
    public int apply(Matrix A, Matrix B, int row, int col) {
        return cachedAdd(A.get(row, col), B.get(row, col));
    }
}