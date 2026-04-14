package org.example.execution;

import org.example.enums.BinaryType;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultMatrixExecutorTest {

    @Test
    void shouldExecuteElementWiseOperation() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 5.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 1.0, 5.0, 6.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 2.0, 6.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 2.0, 6.0, 8.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 3.0, 7.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 3.0, 7.0, 10.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 4.0, 8.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 4.0, 8.0, 12.0)));

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1, 2},
                    {3, 4}
            });
            Matrix b = new Matrix(new int[][]{
                    {5, 6},
                    {7, 8}
            });

            Matrix result = executor.execute(a, b, BinaryType.ADD, "add").get();

            assertEquals(6, result.get(0, 0));
            assertEquals(8, result.get(0, 1));
            assertEquals(10, result.get(1, 0));
            assertEquals(12, result.get(1, 1));

            verify(calculationExecutor).submitBinary(BinaryType.ADD, 1.0, 5.0);
            verify(calculationExecutor).submitBinary(BinaryType.ADD, 2.0, 6.0);
            verify(calculationExecutor).submitBinary(BinaryType.ADD, 3.0, 7.0);
            verify(calculationExecutor).submitBinary(BinaryType.ADD, 4.0, 8.0);
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldExecuteMultiplyUsingDotProduct() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 1.0, 5.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 1.0, 5.0, 5.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 2.0, 7.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 2.0, 7.0, 14.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 1.0, 6.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 1.0, 6.0, 6.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 2.0, 8.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 2.0, 8.0, 16.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 3.0, 5.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 3.0, 5.0, 15.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 4.0, 7.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 4.0, 7.0, 28.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 3.0, 6.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 3.0, 6.0, 18.0)));
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 4.0, 8.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 4.0, 8.0, 32.0)));

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1, 2},
                    {3, 4}
            });
            Matrix b = new Matrix(new int[][]{
                    {5, 6},
                    {7, 8}
            });

            Matrix result = executor.execute(a, b, BinaryType.MULTIPLY, "multiply").get();

            assertEquals(19, result.get(0, 0));
            assertEquals(22, result.get(0, 1));
            assertEquals(43, result.get(1, 0));
            assertEquals(50, result.get(1, 1));
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldReuseCachedMatrixResult() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 5.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 1.0, 5.0, 6.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 2.0, 6.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 2.0, 6.0, 8.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 3.0, 7.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 3.0, 7.0, 10.0)));
            when(calculationExecutor.submitBinary(BinaryType.ADD, 4.0, 8.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 4.0, 8.0, 12.0)));

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1, 2},
                    {3, 4}
            });
            Matrix b = new Matrix(new int[][]{
                    {5, 6},
                    {7, 8}
            });

            Matrix first = executor.execute(a, b, BinaryType.ADD, "add").get();
            Matrix second = executor.execute(a, b, BinaryType.ADD, "add").get();

            assertSame(first, second);
            verify(calculationExecutor, times(4)).submitBinary(any(), anyDouble(), anyDouble());
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldReturnEmptyResultWhenLeftMatrixHasZeroRows() throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[0][0]);
            Matrix b = new Matrix(new int[][]{
                    {1, 2}
            });

            Matrix result = executor.execute(a, b, BinaryType.ADD, "empty").get();

            assertEquals(0, result.data().length);
            verifyNoInteractions(calculationExecutor);
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldReuseCachedMultiplyOperations() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 1.0, 2.0))
                    .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 1.0, 2.0, 2.0)));

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1, 1},
                    {1, 1}
            });
            Matrix b = new Matrix(new int[][]{
                    {2, 2},
                    {2, 2}
            });

            Matrix result = executor.execute(a, b, BinaryType.MULTIPLY, "multiply").get();

            assertEquals(4, result.get(0, 0));
            assertEquals(4, result.get(0, 1));
            assertEquals(4, result.get(1, 0));
            assertEquals(4, result.get(1, 1));

            verify(calculationExecutor, times(1)).submitBinary(BinaryType.MULTIPLY, 1.0, 2.0);
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldCompleteExceptionallyWhenBinaryExecutionFails() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            CompletableFuture<BinaryCalculationRecord> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("boom"));

            when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 3.0)).thenReturn(failed);

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1}
            });
            Matrix b = new Matrix(new int[][]{
                    {3}
            });

            ExecutionException exception = assertThrows(
                    ExecutionException.class,
                    () -> executor.execute(a, b, BinaryType.ADD, "add").get()
            );

            assertNotNull(exception.getCause());
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void shouldShutdownBothPools() throws Exception {
        ExecutorService pool = mock(ExecutorService.class);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        when(pool.awaitTermination(anyLong(), any())).thenReturn(true);

        DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

        executor.shutdown();

        verify(pool).shutdown();
        verify(pool).awaitTermination(10, TimeUnit.SECONDS);
        verify(calculationExecutor).shutdown();
        verify(pool, never()).shutdownNow();
    }

    @Test
    void shouldForceShutdownPoolWhenInterrupted() throws Exception {
        ExecutorService pool = mock(ExecutorService.class);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        when(pool.awaitTermination(anyLong(), any())).thenThrow(new InterruptedException());

        DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

        assertDoesNotThrow(executor::shutdown);

        verify(pool).shutdown();
        verify(pool).shutdownNow();
        verify(calculationExecutor).shutdown();
        assertTrue(Thread.currentThread().isInterrupted());
        assertTrue(Thread.interrupted());
    }

    @Test
    void shouldForceShutdownPoolWhenItDoesNotTerminate() throws Exception {
        ExecutorService pool = mock(ExecutorService.class);
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        when(pool.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS))
                .thenReturn(false);

        DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

        executor.shutdown();

        verify(pool).shutdown();
        verify(pool).awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
        verify(pool).shutdownNow();
        verify(calculationExecutor).shutdown();
    }

    @Test
    void shouldCompleteExceptionallyWhenMultiplyOperationFailsInsideCache() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        DefaultCalculationExecutor calculationExecutor = mock(DefaultCalculationExecutor.class);

        try {
            CompletableFuture<BinaryCalculationRecord> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("boom"));

            when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 1.0, 2.0))
                    .thenReturn(failed);

            DefaultMatrixExecutor executor = new DefaultMatrixExecutor(pool, calculationExecutor);

            Matrix a = new Matrix(new int[][]{
                    {1}
            });
            Matrix b = new Matrix(new int[][]{
                    {2}
            });

            ExecutionException exception = assertThrows(
                    ExecutionException.class,
                    () -> executor.execute(a, b, BinaryType.MULTIPLY, "multiply").get()
            );

            assertNotNull(exception.getCause());
        } finally {
            pool.shutdownNow();
        }
    }
}