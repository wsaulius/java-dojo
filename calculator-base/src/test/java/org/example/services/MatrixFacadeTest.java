package org.example.services;

import org.example.enums.BinaryType;
import org.example.execution.DefaultAsyncMatrixExecutor;
import org.example.models.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class MatrixFacadeTest {

    private DefaultAsyncMatrixExecutor executor;
    private MatrixFacade facade;

    @BeforeEach
    void setUp() {
        executor = mock(DefaultAsyncMatrixExecutor.class);
        facade = new MatrixFacade(executor);
    }

    @Test
    void computeAsync_delegatesToExecutor() {
        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});
        BinaryType type = BinaryType.ADD;

        CompletableFuture<Matrix> expected =
                CompletableFuture.completedFuture(new Matrix(new int[][]{{3}}));

        when(executor.submit(a, b, type, "op")).thenReturn(expected);

        CompletableFuture<Matrix> result =
                facade.computeAsync(a, b, type, "op");

        assertSame(expected, result);
        verify(executor).submit(a, b, type, "op");
    }

    @Test
    void shutdown_delegatesToExecutor() {
        facade.shutdown();
        verify(executor).shutdown();
    }
}