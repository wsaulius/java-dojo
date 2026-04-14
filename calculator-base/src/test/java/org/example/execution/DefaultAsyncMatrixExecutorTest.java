package org.example.execution;

import org.example.enums.BinaryType;
import org.example.models.BinaryCalculationRecord;
import org.example.models.Matrix;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultAsyncMatrixExecutorTest {

    @Test
    void shouldExecuteElementWiseOperation() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);

        when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 5.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 1.0, 5.0, 6.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 2.0, 6.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 2.0, 6.0, 8.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 3.0, 7.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 3.0, 7.0, 10.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 4.0, 8.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 4.0, 8.0, 12.0)));

        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        Matrix a = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });
        Matrix b = new Matrix(new int[][]{
                {5, 6},
                {7, 8}
        });

        Matrix result = executor.submit(a, b, BinaryType.ADD, "add").join();

        assertEquals(6, result.get(0, 0));
        assertEquals(8, result.get(0, 1));
        assertEquals(10, result.get(1, 0));
        assertEquals(12, result.get(1, 1));
    }

    @Test
    void shouldExecuteMultiplyUsingDotProduct() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);

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

        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        Matrix a = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });
        Matrix b = new Matrix(new int[][]{
                {5, 6},
                {7, 8}
        });

        Matrix result = executor.submit(a, b, BinaryType.MULTIPLY, "multiply").join();

        assertEquals(19, result.get(0, 0));
        assertEquals(22, result.get(0, 1));
        assertEquals(43, result.get(1, 0));
        assertEquals(50, result.get(1, 1));
    }

    @Test
    void shouldReuseCachedMatrixResult() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);

        when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 5.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 1.0, 5.0, 6.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 2.0, 6.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 2.0, 6.0, 8.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 3.0, 7.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 3.0, 7.0, 10.0)));
        when(calculationExecutor.submitBinary(BinaryType.ADD, 4.0, 8.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.ADD, 4.0, 8.0, 12.0)));

        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        Matrix a = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });
        Matrix b = new Matrix(new int[][]{
                {5, 6},
                {7, 8}
        });

        Matrix first = executor.submit(a, b, BinaryType.ADD, "add").join();
        Matrix second = executor.submit(a, b, BinaryType.ADD, "add").join();

        assertSame(first, second);
        verify(calculationExecutor, times(4)).submitBinary(any(), anyDouble(), anyDouble());
    }

    @Test
    void shouldReuseCachedMultiplyOperations() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);

        when(calculationExecutor.submitBinary(BinaryType.MULTIPLY, 1.0, 2.0))
                .thenReturn(CompletableFuture.completedFuture(new BinaryCalculationRecord(BinaryType.MULTIPLY, 1.0, 2.0, 2.0)));

        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        Matrix a = new Matrix(new int[][]{
                {1, 1},
                {1, 1}
        });
        Matrix b = new Matrix(new int[][]{
                {2, 2},
                {2, 2}
        });

        Matrix result = executor.submit(a, b, BinaryType.MULTIPLY, "multiply").join();

        assertEquals(4, result.get(0, 0));
        assertEquals(4, result.get(0, 1));
        assertEquals(4, result.get(1, 0));
        assertEquals(4, result.get(1, 1));

        verify(calculationExecutor, times(1)).submitBinary(BinaryType.MULTIPLY, 1.0, 2.0);
    }

    @Test
    void shouldCompleteExceptionallyWhenBinaryExecutionFails() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);

        CompletableFuture<BinaryCalculationRecord> failed = new CompletableFuture<>();
        failed.completeExceptionally(new IllegalStateException("boom"));

        when(calculationExecutor.submitBinary(BinaryType.ADD, 1.0, 3.0)).thenReturn(failed);

        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        Matrix a = new Matrix(new int[][]{
                {1}
        });
        Matrix b = new Matrix(new int[][]{
                {3}
        });

        assertThrows(
                RuntimeException.class,
                () -> executor.submit(a, b, BinaryType.ADD, "add").join()
        );
    }

    @Test
    void shouldShutdownDelegateExecutor() {
        DefaultAsyncCalculationExecutor calculationExecutor = mock(DefaultAsyncCalculationExecutor.class);
        DefaultAsyncMatrixExecutor executor = new DefaultAsyncMatrixExecutor(calculationExecutor);

        executor.shutdown();

        verify(calculationExecutor).shutdown();
    }
}