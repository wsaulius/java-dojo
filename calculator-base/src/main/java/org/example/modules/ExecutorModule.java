package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.execution.DefaultCalculationExecutor;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.interfaces.annotations.CalcPool;
import org.example.interfaces.CalculationExecutor;
import org.example.interfaces.annotations.MatrixPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Guice module that binds calculation executors and provides
 * dedicated executor services for calculation and matrix workloads.
 */
public class ExecutorModule extends AbstractModule {

    /**
     * Configures executor bindings.
     */
    @Override
    protected void configure() {
        bind(CalculationExecutor.class).to(DefaultCalculationExecutor.class);
        bind(AsyncCalculationExecutor.class).to(DefaultAsyncCalculationExecutor.class);
    }

    /**
     * Provides the executor service used by matrix execution logic.
     *
     * @return matrix executor service
     */
    @Provides
    @Singleton
    @MatrixPool
    ExecutorService provideMatrixExecutor() {
        return Executors.newFixedThreadPool(8);
    }

    /**
     * Provides the executor service used by calculation execution logic.
     *
     * @return calculation executor service
     */
    @Provides
    @Singleton
    @CalcPool
    ExecutorService provideCalculationExecutor() {
        return Executors.newCachedThreadPool();
    }
}