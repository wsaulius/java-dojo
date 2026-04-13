package org.example.demo;

import org.example.enums.BinaryType;
import org.example.execution.DefaultCalculationExecutor;
import org.example.execution.DefaultMatrixExecutor;
import org.example.models.Matrix;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                    System.out.println(now() + " [CALC] started on " + Thread.currentThread().getName());
                    Thread.sleep(300);
                    double left = inv.getArgument(1);
                    double right = inv.getArgument(2);
                    double result = left + right;
                    System.out.println(now() + " [CALC] finished: " + left + " + " + right + " = " + result);
                    return result;
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1,2},{3,4}});
        Matrix b = new Matrix(new int[][]{{5,6},{7,8}});

        System.out.println(now() + " [MAIN] submitting matrix task");

        Future<Matrix> future = executor.execute(a, b, BinaryType.ADD, "add");

        System.out.println(now() + " [MAIN] submitted matrix task");
        System.out.println(now() + " [MAIN] waiting for result");

        Matrix result = future.get();

        System.out.println(now() + " [MAIN] completed");
        System.out.println(now() + " [MAIN] result[0,0] = " + result.get(0,0));
        System.out.println(now() + " [MAIN] result[0,1] = " + result.get(0,1));
        System.out.println(now() + " [MAIN] result[1,0] = " + result.get(1,0));
        System.out.println(now() + " [MAIN] result[1,1] = " + result.get(1,1));

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
                    System.out.println(now() + " [CALC] started delayed calculation on " + Thread.currentThread().getName());
                    Thread.sleep(500);
                    double left = inv.getArgument(1);
                    double right = inv.getArgument(2);
                    double result = left + right;
                    System.out.println(now() + " [CALC] finished delayed calculation = " + result);
                    return result;
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        long start = System.currentTimeMillis();

        System.out.println(now() + " [MAIN] calling execute().get()");

        Matrix result = executor.execute(a, b, BinaryType.ADD, "add").get();

        long duration = System.currentTimeMillis() - start;

        System.out.println(now() + " [MAIN] duration = " + duration);
        System.out.println(now() + " [MAIN] result = " + result.get(0,0));

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
                    int count = calls.incrementAndGet();
                    double left = inv.getArgument(1);
                    double right = inv.getArgument(2);
                    double result = left + right;

                    System.out.println(now() + " [CALC] REAL EXECUTION #" + count);
                    System.out.println(now() + " [CALC] " + left + " + " + right + " = " + result);

                    return result;
                });

        DefaultCalculationExecutor calcExecutor =
                new DefaultCalculationExecutor(service, calcPool);

        DefaultMatrixExecutor executor =
                new DefaultMatrixExecutor(matrixPool, calcExecutor);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        System.out.println(now() + " [MAIN] first execution");
        Matrix first = executor.execute(a, b, BinaryType.ADD, "add").get();
        System.out.println(now() + " [MAIN] first result = " + first.get(0,0));

        System.out.println(now() + " [MAIN] second execution");
        Matrix second = executor.execute(a, b, BinaryType.ADD, "add").get();
        System.out.println(now() + " [MAIN] second result = " + second.get(0,0));

        System.out.println(now() + " [MAIN] total real calls = " + calls.get());

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

        System.out.println(now() + " [MAIN] shutting down executor");

        executor.shutdown();

        System.out.println(now() + " [MAIN] matrixPool shutdown = " + matrixPool.isShutdown());
        System.out.println(now() + " [MAIN] calcPool shutdown = " + calcPool.isShutdown());

        assertTrue(matrixPool.isShutdown());
    }

    private String now() {
        return LocalTime.now().toString();
    }
}