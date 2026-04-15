package org.example.execution;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.enums.BinaryType;
import org.example.interfaces.MatrixExecutor;
import org.example.interfaces.annotations.MatrixPool;
import org.example.models.Matrix;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Executes matrix operations using an ExecutorService.
 */
@Singleton
public final class DefaultMatrixExecutor implements MatrixExecutor {

    private final ThreadPoolExecutor pool;
    private final DefaultCalculationExecutor executor;
    private final ConcurrentHashMap<String, Matrix> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> operationCache = new ConcurrentHashMap<>();

    /**
     * Creates executor with required dependencies.
     */
    @Inject
    public DefaultMatrixExecutor(
            @MatrixPool ThreadPoolExecutor pool,
            DefaultCalculationExecutor executor
    ) {
        this.pool = pool;
        this.executor = executor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<Matrix> execute(
            Matrix a,
            Matrix b,
            BinaryType type,
            String operationName
    ) {
        String key = generateKey(a, b, operationName);

        Matrix cached = cache.get(key);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        int rows = a.rows();
        int cols = b.cols();
        Matrix result = new Matrix(new int[rows][cols]);
        CompletableFuture<Matrix> matrixFuture = new CompletableFuture<>();

        if (rows == 0) {
            cache.put(key, result);
            matrixFuture.complete(result);
            return matrixFuture;
        }

        AtomicInteger remainingRows = new AtomicInteger(rows);

        List<?> ignored = IntStream.range(0, rows)
                .mapToObj(row -> pool.submit(() -> {
                    try {
                        for (int col = 0; col < cols; col++) {
                            int value;

                            if (type == BinaryType.MULTIPLY) {
                                final int currentCol = col;
                                value = IntStream.range(0, a.cols())
                                        .map(k -> {
                                            int left = a.get(row, k);
                                            int right = b.get(k, currentCol);
                                            String opKey = left + ":" + type + ":" + right;

                                            return operationCache.computeIfAbsent(opKey, kk -> {
                                                try {
                                                    return (int) executor.submitBinary(
                                                            type,
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
                                        (double) a.get(row, col),
                                        (double) b.get(row, col)
                                ).get().result();
                            }

                            result.set(row, col, value);
                        }

                        if (remainingRows.decrementAndGet() == 0) {
                            cache.put(key, result);
                            matrixFuture.complete(result);
                        }
                    } catch (Exception e) {
                        matrixFuture.completeExceptionally(e);
                    }
                }))
                .toList();

        return matrixFuture;
    }

    /**
     * Generates a cache key for matrix operations based on identity and operation name.
     */
    private String generateKey(Matrix a, Matrix b, String operationName) {
        return operationName + ":" + System.identityHashCode(a) + ":" + System.identityHashCode(b);
    }

    /** {@inheritDoc} */
    @Override
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
        executor.shutdown();
    }
}