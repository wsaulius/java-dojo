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

import java.util.concurrent.*;

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
    ThreadPoolExecutor provideMatrixExecutor() {
        return new ThreadPoolExecutor(
                8,                  // corePoolSize
                8,                  // maximumPoolSize
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }


    /**
     * Provides the executor service used by calculation execution logic.
     *
     * @return calculation executor service
     */
    @Provides
    @Singleton
    @CalcPool
    ThreadPoolExecutor provideCalculationExecutor() {
        return new ThreadPoolExecutor(
                2,                  // corePoolSize
                32,                 // maximumPoolSize
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
    }
}