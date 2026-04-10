package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.execution.DefaultCalculationExecutor;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.interfaces.CalculationExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CalculationExecutor.class).to(DefaultCalculationExecutor.class);
        bind(AsyncCalculationExecutor.class).to(DefaultAsyncCalculationExecutor.class);
    }

    /**
     * Executor for MatrixExecutor (row + cell parallelism)
     */
    @Provides
    @Singleton
    @MatrixPool
    ExecutorService provideMatrixExecutor() {
        return Executors.newFixedThreadPool(8);
    }

    /**
     * Executor for CalculationExecutor (binary operations)
     */
    @Provides
    @Singleton
    @CalcPool
    ExecutorService provideCalculationExecutor() {
        return Executors.newCachedThreadPool();
    }
}