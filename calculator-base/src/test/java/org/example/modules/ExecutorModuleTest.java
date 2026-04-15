package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.interfaces.annotations.CalcPool;
import org.example.interfaces.CalculationExecutor;
import org.example.interfaces.annotations.MatrixPool;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ExecutorModuleTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(
                new ExecutorModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(CalculatorService.class).toInstance(mock(CalculatorService.class));
                    }
                }
        );
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveExecutionLayer() {
        assertNotNull(injector.getInstance(CalculationExecutor.class));
        assertNotNull(injector.getInstance(AsyncCalculationExecutor.class));
        assertNotNull(injector.getInstance(Key.get(ThreadPoolExecutor.class, CalcPool.class)));
        assertNotNull(injector.getInstance(Key.get(ThreadPoolExecutor.class, MatrixPool.class)));
    }

    @Test
    void shouldProvideSingletonExecutorService() {
        ExecutorService first = injector.getInstance(
                Key.get(ThreadPoolExecutor.class, CalcPool.class));
        ExecutorService second = injector.getInstance(
                Key.get(ThreadPoolExecutor.class, CalcPool.class));

        assertSame(first, second);
    }
}