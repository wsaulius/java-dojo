package org.example.execution;

import org.example.enums.BinaryType;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DefaultAsyncMatrixExecutorTest {

    private DefaultAsyncCalculationExecutor calcExecutor;
    private ThreadPoolExecutor pool;
    private DefaultAsyncMatrixExecutor executor;

    @BeforeEach
    void setUp() {
        calcExecutor = mock(DefaultAsyncCalculationExecutor.class);

        pool = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        executor = new DefaultAsyncMatrixExecutor(calcExecutor, pool);
    }

    @AfterEach
    void tearDown() {
        pool.shutdownNow();
    }

    @Test
    void submit_add_computesMatrix() {
        Matrix a = new Matrix(new int[][]{{1, 2}});
        Matrix b = new Matrix(new int[][]{{3, 4}});

        when(calcExecutor.submitBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> CompletableFuture.completedFuture(
                        new BinaryCalculationRecord(
                                BinaryType.ADD,
                                inv.getArgument(1),
                                inv.getArgument(2),
                                (Double) inv.getArgument(1) + (Double) inv.getArgument(2)
                        )
                ));

        Matrix result = executor.submit(a, b, BinaryType.ADD, "add").join();

        assertEquals(4, result.get(0, 0));
        assertEquals(6, result.get(0, 1));

        verify(calcExecutor, times(2)).submitBinary(eq(BinaryType.ADD), anyDouble(), anyDouble());
    }

    @Test
    void submit_multiply_computesMatrix() {
        pool.setMaximumPoolSize(2);
        pool.setCorePoolSize(2);

        Matrix a = new Matrix(new int[][]{{1, 2}});
        Matrix b = new Matrix(new int[][]{
                {3},
                {4}
        });

        when(calcExecutor.submitBinary(eq(BinaryType.MULTIPLY), anyDouble(), anyDouble()))
                .thenAnswer(inv -> CompletableFuture.completedFuture(
                        new BinaryCalculationRecord(
                                BinaryType.MULTIPLY,
                                inv.getArgument(1),
                                inv.getArgument(2),
                                (Double) inv.getArgument(1) * (Double) inv.getArgument(2)
                        )
                ));

        Matrix result = executor.submit(a, b, BinaryType.MULTIPLY, "mul").join();

        assertEquals(11, result.get(0, 0));

        verify(calcExecutor, times(2)).submitBinary(eq(BinaryType.MULTIPLY), anyDouble(), anyDouble());
    }

    @Test
    void submit_resultCache_returnsSameInstance() {
        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        when(calcExecutor.submitBinary(any(), anyDouble(), anyDouble()))
                .thenReturn(CompletableFuture.completedFuture(
                        new BinaryCalculationRecord(BinaryType.ADD, 1, 2, 3)
                ));

        Matrix first = executor.submit(a, b, BinaryType.ADD, "cache").join();
        Matrix second = executor.submit(a, b, BinaryType.ADD, "cache").join();

        assertSame(first, second);
        verify(calcExecutor, times(1)).submitBinary(any(), anyDouble(), anyDouble());
    }

    @Test
    void submit_operationCache_reusesSameOperation() {
        Matrix a = new Matrix(new int[][]{{1, 1}});
        Matrix b = new Matrix(new int[][]{{2, 2}});

        when(calcExecutor.submitBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenReturn(CompletableFuture.completedFuture(
                        new BinaryCalculationRecord(BinaryType.ADD, 1, 2, 3)
                ));

        Matrix result = executor.submit(a, b, BinaryType.ADD, "op-cache").join();

        assertEquals(3, result.get(0, 0));
        assertEquals(3, result.get(0, 1));

        verify(calcExecutor, times(1)).submitBinary(eq(BinaryType.ADD), eq(1.0), eq(2.0));
    }

    @Test
    void submit_propagatesException() {
        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        CompletableFuture<BinaryCalculationRecord> failed = new CompletableFuture<>();
        failed.completeExceptionally(new RuntimeException());

        when(calcExecutor.submitBinary(eq(BinaryType.ADD), eq(1.0), eq(2.0)))
                .thenReturn(failed);

        assertThrows(
                CompletionException.class,
                () -> executor.submit(a, b, BinaryType.ADD, "fail").join()
        );
    }

    @Test
    void buildCellFuture_add_returnsElementWiseValue() throws Exception {
        Matrix a = new Matrix(new int[][]{
                {7}
        });
        Matrix b = new Matrix(new int[][]{
                {5}
        });

        when(calcExecutor.submitBinary(eq(BinaryType.ADD), eq(7.0), eq(5.0)))
                .thenReturn(CompletableFuture.completedFuture(
                        new BinaryCalculationRecord(BinaryType.ADD, 7.0, 5.0, 12.0)
                ));

        Method method = DefaultAsyncMatrixExecutor.class.getDeclaredMethod(
                "buildCellFuture",
                Matrix.class,
                Matrix.class,
                BinaryType.class,
                int.class,
                int.class
        );
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        CompletableFuture<Integer> result =
                (CompletableFuture<Integer>) method.invoke(executor, a, b, BinaryType.ADD, 0, 0);

        assertEquals(12, result.join());
        verify(calcExecutor).submitBinary(eq(BinaryType.ADD), eq(7.0), eq(5.0));
    }

    @Test
    void buildCellFuture_multiply_returnsDotProductValue() throws Exception {
        Matrix a = new Matrix(new int[][]{
                {1, 2}
        });
        Matrix b = new Matrix(new int[][]{
                {3},
                {4}
        });

        when(calcExecutor.submitBinary(eq(BinaryType.MULTIPLY), anyDouble(), anyDouble()))
                .thenAnswer(invocation -> {
                    double left = invocation.getArgument(1);
                    double right = invocation.getArgument(2);
                    return CompletableFuture.completedFuture(
                            new BinaryCalculationRecord(BinaryType.MULTIPLY, left, right, left * right)
                    );
                });

        Method method = DefaultAsyncMatrixExecutor.class.getDeclaredMethod(
                "buildCellFuture",
                Matrix.class,
                Matrix.class,
                BinaryType.class,
                int.class,
                int.class
        );
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        CompletableFuture<Integer> result =
                (CompletableFuture<Integer>) method.invoke(executor, a, b, BinaryType.MULTIPLY, 0, 0);

        assertEquals(11, result.join());
        verify(calcExecutor, times(2)).submitBinary(eq(BinaryType.MULTIPLY), anyDouble(), anyDouble());
    }

    @Test
    void shutdown_delegates() {
        executor.shutdown();
        verify(calcExecutor).shutdown();
    }
}