package org.example.demo;

import org.example.enums.BinaryType;
import org.example.execution.DefaultCalculationExecutor;
import org.example.execution.DefaultMatrixExecutor;
import org.example.models.Matrix;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DefaultMatrixExecutorDemoTest {

    private ExecutorService matrixPool;
    private ExecutorService calcPool;

    @BeforeEach
    void print(TestInfo info) {
        System.out.println("\n==== " + info.getDisplayName() + " ====");
    }

    @AfterEach
    void cleanup() {
        if (matrixPool != null) matrixPool.shutdownNow();
        if (calcPool != null) calcPool.shutdownNow();
    }

    @Test
    @DisplayName("Demo 1 - row-level parallel execution")
    void demo_parallelRows() throws Exception {
        CalculatorService service = mock(CalculatorService.class);

        calcPool = Executors.newFixedThreadPool(2);
        matrixPool = Executors.newFixedThreadPool(2);

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    System.out.println(now() + " calc on " + Thread.currentThread().getName());
                    Thread.sleep(300);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1,2},{3,4}});
        Matrix b = new Matrix(new int[][]{{5,6},{7,8}});

        Future<Matrix> future = executor.execute(a, b, BinaryType.ADD, "add");

        System.out.println(now() + " submitted matrix task");

        Matrix result = future.get();

        System.out.println(now() + " completed");

        assertEquals(6, result.get(0,0));
        assertEquals(8, result.get(0,1));
        assertEquals(10, result.get(1,0));
        assertEquals(12, result.get(1,1));
    }

    @Test
    @DisplayName("Demo 2 - blocking Future.get()")
    void demo_blockingGet() throws Exception {
        CalculatorService service = mock(CalculatorService.class);

        calcPool = Executors.newFixedThreadPool(1);
        matrixPool = Executors.newFixedThreadPool(1);

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    Thread.sleep(500);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        long start = System.currentTimeMillis();

        Matrix result = executor.execute(a, b, BinaryType.ADD, "add").get();

        long duration = System.currentTimeMillis() - start;

        System.out.println(now() + " duration = " + duration);

        assertTrue(duration >= 500);
        assertEquals(3, result.get(0,0));
    }

    @Test
    @DisplayName("Demo 3 - caching avoids recomputation")
    void demo_cache() throws Exception {
        CalculatorService service = mock(CalculatorService.class);

        calcPool = Executors.newFixedThreadPool(1);
        matrixPool = Executors.newFixedThreadPool(1);

        AtomicInteger calls = new AtomicInteger();

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    calls.incrementAndGet();
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        executor.execute(a, b, BinaryType.ADD, "add").get();
        executor.execute(a, b, BinaryType.ADD, "add").get();

        System.out.println("calls = " + calls.get());

        assertEquals(1, calls.get());
    }

    @Test
    @DisplayName("Demo 4 - shutdown stops execution")
    void demo_shutdown() {
        CalculatorService service = mock(CalculatorService.class);

        calcPool = Executors.newFixedThreadPool(1);
        matrixPool = Executors.newFixedThreadPool(1);

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        executor.shutdown();

        assertTrue(matrixPool.isShutdown());
    }

    private String now() {
        return LocalTime.now().toString();
    }
}