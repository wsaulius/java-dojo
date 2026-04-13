package org.example.execution;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.AsyncMatrixExecutor;
import org.example.models.Matrix;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Asynchronous matrix executor based on {@link CompletableFuture} composition.
 *
 * <p>This implementation performs matrix operations in a fully non-blocking manner:
 * <ul>
 *     <li>Each matrix cell is computed as a {@link CompletableFuture}.</li>
 *     <li>Row computations are composed from cell-level futures.</li>
 *     <li>The final matrix result is composed from all row futures.</li>
 * </ul>
 *
 * <p>Binary operations are delegated to {@link DefaultAsyncCalculationExecutor},
 * ensuring all underlying calculations are also executed asynchronously.
 *
 * <p>A cache is maintained for:
 * <ul>
 *     <li>Completed matrix results (by operation key).</li>
 *     <li>Individual binary operations (to avoid duplicate computation).</li>
 * </ul>
 *
 * <p>This executor does not block internally; it returns a {@link CompletableFuture}
 * that completes when the full matrix computation is finished.
 */
public final class DefaultAsyncMatrixExecutor implements AsyncMatrixExecutor {

    private final DefaultAsyncCalculationExecutor executor;
    private final ConcurrentHashMap<String, Matrix> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CompletableFuture<Integer>> operationCache = new ConcurrentHashMap<>();

    /**
     * Constructs the async matrix executor.
     *
     * @param executor async calculation executor used for binary operations
     */
    @Inject
    public DefaultAsyncMatrixExecutor(DefaultAsyncCalculationExecutor executor) {
        this.executor = executor;
    }

    /**
     * Submits a matrix operation for asynchronous execution.
     *
     * <p>If a cached result exists for the given inputs and operation name,
     * a completed future is returned immediately.
     *
     * @param a left matrix
     * @param b right matrix
     * @param type binary operation type
     * @param operationName operation identifier used for caching
     * @return future containing the resulting matrix
     */
    @Override
    public CompletableFuture<Matrix> submit(
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

        List<CompletableFuture<Void>> rowTasks = IntStream.range(0, rows)
                .mapToObj(row -> buildRowFuture(a, b, type, result, row))
                .toList();

        return CompletableFuture.allOf(rowTasks.toArray(new CompletableFuture[0]))
                .thenApply(ignored -> {
                    cache.put(key, result);
                    return result;
                });
    }

    /**
     * Builds an asynchronous computation for a single matrix row.
     *
     * @param a left matrix
     * @param b right matrix
     * @param type operation type
     * @param result target matrix
     * @param row row index
     * @return future completing when the row is fully computed
     */
    private CompletableFuture<Void> buildRowFuture(
            Matrix a,
            Matrix b,
            BinaryType type,
            Matrix result,
            int row
    ) {
        int cols = b.cols();

        List<CompletableFuture<Void>> cellTasks = IntStream.range(0, cols)
                .mapToObj(col -> buildCellFuture(a, b, type, row, col)
                        .thenAccept(value -> result.set(row, col, value)))
                .toList();

        return CompletableFuture.allOf(cellTasks.toArray(new CompletableFuture[0]));
    }

    /**
     * Builds an asynchronous computation for a single matrix cell.
     *
     * <p>For multiplication, computes the dot product asynchronously across the
     * matching row and column. For other operations, computes the element-wise value.
     *
     * @param a left matrix
     * @param b right matrix
     * @param type operation type
     * @param row row index
     * @param col column index
     * @return future producing the computed cell value
     */
    private CompletableFuture<Integer> buildCellFuture(
            Matrix a,
            Matrix b,
            BinaryType type,
            int row,
            int col
    ) {
        if (type == BinaryType.MULTIPLY) {
            List<CompletableFuture<Integer>> parts = IntStream.range(0, a.cols())
                    .mapToObj(k -> resolveOperation(type, a.get(row, k), b.get(k, col)))
                    .toList();

            return CompletableFuture.allOf(parts.toArray(new CompletableFuture[0]))
                    .thenApply(ignored -> parts.stream()
                            .mapToInt(CompletableFuture::join)
                            .sum());
        }

        return resolveOperation(type, a.get(row, col), b.get(row, col));
    }

    /**
     * Resolves a binary operation asynchronously with caching.
     *
     * @param type operation type
     * @param left left operand
     * @param right right operand
     * @return future producing the computed value
     */
    private CompletableFuture<Integer> resolveOperation(
            BinaryType type,
            int left,
            int right
    ) {
        String opKey = left + ":" + type + ":" + right;

        return operationCache.computeIfAbsent(opKey, key ->
                executor.submitBinary(type, (double) left, (double) right)
                        .thenApply(record -> (int) record.result())
        );
    }

    /**
     * Generates a cache key for matrix operations based on identity and operation name.
     *
     * @param a left matrix
     * @param b right matrix
     * @param operationName operation identifier
     * @return unique cache key
     */
    private String generateKey(Matrix a, Matrix b, String operationName) {
        return operationName + ":" + System.identityHashCode(a) + ":" + System.identityHashCode(b);
    }

    /** {@inheritDoc} */
    @Override
    public void shutdown() {
        executor.shutdown();
    }
}