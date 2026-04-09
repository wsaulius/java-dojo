package org.example.services;

import org.example.interfaces.MatrixOperation;
import org.example.models.Matrix;

import java.util.concurrent.*;
import java.util.*;
import java.util.stream.IntStream;

public final class MatrixService {

    private final ExecutorService pool;

    // 🔹 Cache
    private final ConcurrentHashMap<String, Matrix> cache = new ConcurrentHashMap<>();

    public MatrixService(int threads) {
        this.pool = Executors.newFixedThreadPool(threads);
    }

    public Matrix execute(Matrix A, Matrix B,
                          MatrixOperation operation,
                          String operationName) {

        String key = generateKey(A, B, operationName);

        Matrix cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        int rows = A.rows();
        int cols = B.cols();

        Matrix result = new Matrix(new int[rows][cols]);

        List<CompletableFuture<Void>> tasks =
                IntStream.range(0, rows)
                        .mapToObj(row ->
                                CompletableFuture.runAsync(() -> {
                                    for (int col = 0; col < cols; col++) {
                                        int value = operation.apply(A, B, row, col);
                                        result.set(row, col, value);
                                    }
                                    System.out.println("Row " + row +
                                            " computed by " + Thread.currentThread().getName());
                                }, pool)
                        )
                        .toList();

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        cache.put(key, result);

        return result;
    }

    private String generateKey(Matrix A, Matrix B, String op) {
        return Arrays.deepToString(A.data()) +
                Arrays.deepToString(B.data()) +
                op;
    }

    public void shutdown() {
        pool.shutdown();
    }
}