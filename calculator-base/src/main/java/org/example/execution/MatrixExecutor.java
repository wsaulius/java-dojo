package org.example.execution;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.CalculationExecutor;
import org.example.models.Matrix;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public final class MatrixExecutor {

    private final ExecutorService pool;
    private final ExecutorService calcPool;
    private final CalculationExecutor executor;
    private final ConcurrentHashMap<String, Matrix> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> operationCache = new ConcurrentHashMap<>();

    @Inject
    public MatrixExecutor(ExecutorService pool, CalculationExecutor executor) {
        this.pool = pool;
        this.calcPool = Executors.newCachedThreadPool();
        this.executor = executor;
    }

    public Matrix execute(Matrix A, Matrix B, BinaryType type, String operationName) {

        String key = generateKey(A, B, operationName);

        Matrix cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        int rows = A.rows();
        int cols = B.cols();
        Matrix result = new Matrix(new int[rows][cols]);

        List<CompletableFuture<Void>> tasks = IntStream.range(0, rows)
                .mapToObj(row -> CompletableFuture.runAsync(() -> {
                    IntStream.range(0, cols).forEach(col -> {
                        try {
                            int value;
                            if (type == BinaryType.MULTIPLY) {
                                value = IntStream.range(0, A.cols())
                                        .map(k -> {
                                            int left = A.get(row, k);
                                            int right = B.get(k, col);
                                            String opKey = left + ":" + BinaryType.MULTIPLY + ":" + right;
                                            return operationCache.computeIfAbsent(opKey, kk -> {
                                                try {
                                                    return (int) executor.submitBinary(
                                                            BinaryType.MULTIPLY,
                                                            (double) left,
                                                            (double) right
                                                    ).get().result();
                                                } catch (Exception e) {
                                                    throw new RuntimeException(e);
                                                }
                                            });
                                        })
                                        .sum();
                            } else {
                                value = (int) executor.submitBinary(
                                        type,
                                        (double) A.get(row, col),
                                        (double) B.get(row, col)
                                ).get().result();
                            }
                            result.set(row, col, value);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }, calcPool))
                .toList();

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        cache.put(key, result);
        return result;
    }

    private String generateKey(Matrix A, Matrix B, String operationName) {
        return operationName + ":" + System.identityHashCode(A) + ":" + System.identityHashCode(B);
    }

    public void shutdown() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        calcPool.shutdown();
        try {
            if (!calcPool.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
                calcPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            calcPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}