package org.example.execution;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.AsyncMatrixExecutor;
import org.example.models.Matrix;
import org.example.interfaces.annotations.MatrixPool;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * Executes Async matrix operations using an ExecutorService.
 */
public final class DefaultAsyncMatrixExecutor implements AsyncMatrixExecutor {

    private final ExecutorService pool;
    private final DefaultAsyncCalculationExecutor executor;
    private final ConcurrentHashMap<String, Matrix> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> operationCache = new ConcurrentHashMap<>();

    /**
     * Creates executor with required dependencies.
     */
    @Inject
    public DefaultAsyncMatrixExecutor(@MatrixPool ExecutorService pool,
                                      DefaultAsyncCalculationExecutor executor) {
        this.pool = pool;
        this.executor = executor;
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Matrix> submit(Matrix a, Matrix b, BinaryType type, String operationName) {
        String key = generateKey(a, b, operationName);

        Matrix cached = cache.get(key);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        return CompletableFuture.supplyAsync(() -> {
            int rows = a.rows();
            int cols = b.cols();
            Matrix result = new Matrix(new int[rows][cols]);

            List<CompletableFuture<Void>> tasks = IntStream.range(0, rows)
                    .mapToObj(row -> CompletableFuture.runAsync(() -> {
                        IntStream.range(0, cols).forEach(col -> {
                            try {
                                int value;
                                if (type == BinaryType.MULTIPLY) {
                                    value = IntStream.range(0, a.cols())
                                            .map(k -> {
                                                int left = a.get(row, k);
                                                int right = b.get(k, col);
                                                String opKey = left + ":" + type + ":" + right;

                                                return operationCache.computeIfAbsent(opKey, kk -> {
                                                    try {
                                                        return (int) executor.submitBinary(
                                                                type,
                                                                (double) left,
                                                                (double) right
                                                        ).join().result();
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
                                    ).join().result();
                                }

                                result.set(row, col, value);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }, pool))
                    .toList();

            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
            cache.put(key, result);
            return result;
        }, pool);
    }

    /**
     * Generates a cache key for matrix operations based on identity and operation name.
     */
    private String generateKey(Matrix a, Matrix b, String operationName) {
        return operationName + ":" + System.identityHashCode(a) + ":" + System.identityHashCode(b);
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
        executor.shutdown();
    }
}