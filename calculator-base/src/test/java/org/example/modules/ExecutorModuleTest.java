package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.interfaces.CalculationExecutor;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

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
        assertNotNull(injector.getInstance(ExecutorService.class));
    }

    @Test
    void shouldProvideSingletonExecutorService() {
        ExecutorService first = injector.getInstance(ExecutorService.class);
        ExecutorService second = injector.getInstance(ExecutorService.class);

        assertSame(first, second);
    }
}