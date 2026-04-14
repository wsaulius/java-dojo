package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.enums.BinaryType;
import org.example.execution.DefaultAsyncMatrixExecutor;
import org.example.models.Matrix;

import java.util.concurrent.CompletableFuture;

@Singleton
public class MatrixFacade {

    private final DefaultAsyncMatrixExecutor executor;

    @Inject
    public MatrixFacade(DefaultAsyncMatrixExecutor executor) {
        this.executor = executor;
    }

    public CompletableFuture<Matrix> computeAsync(
            Matrix a,
            Matrix b,
            BinaryType type,
            String opName
    ) {
        return executor.submit(a, b, type, opName);
    }

    public void shutdown() {
        executor.shutdown();
    }
}