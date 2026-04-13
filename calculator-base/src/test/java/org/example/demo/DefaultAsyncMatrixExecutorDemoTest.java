package org.example.demo;

import org.example.enums.BinaryType;
import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.execution.DefaultAsyncMatrixExecutor;
import org.example.models.Matrix;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DefaultAsyncMatrixExecutorDemoTest {

    private ExecutorService pool;

    @BeforeEach
    void print(TestInfo info) {
        System.out.println("\n==== " + info.getDisplayName() + " ====");
    }

    @AfterEach
    void cleanup() {
        if (pool != null) pool.shutdownNow();
    }

    @Test
    @DisplayName("Demo 1 - async matrix execution (non-blocking)")
    void demo_asyncNonBlocking() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool = Executors.newFixedThreadPool(2);

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    Thread.sleep(500);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        System.out.println(now() + " submit async");

        CompletableFuture<Matrix> future =
                executor.submit(a, b, BinaryType.ADD, "add");

        System.out.println(now() + " after submit done? " + future.isDone());

        Matrix result = future.get();

        System.out.println(now() + " result = " + result.get(0,0));

        assertEquals(3, result.get(0,0));
    }

    @Test
    @DisplayName("Demo 2 - cell-level parallelism")
    void demo_parallelCells() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool = Executors.newFixedThreadPool(4);

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    System.out.println(now() + " calc " + Thread.currentThread().getName());
                    Thread.sleep(300);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1,2},{3,4}});
        Matrix b = new Matrix(new int[][]{{5,6},{7,8}});

        Matrix result = executor.submit(a, b, BinaryType.ADD, "add").get();

        assertEquals(6, result.get(0,0));
    }

    @Test
    @DisplayName("Demo 3 - caching avoids recomputation")
    void demo_cache() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool = Executors.newFixedThreadPool(2);

        AtomicInteger calls = new AtomicInteger();

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    calls.incrementAndGet();
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        executor.submit(a, b, BinaryType.ADD, "add").get();
        executor.submit(a, b, BinaryType.ADD, "add").get();

        System.out.println("calls = " + calls.get());

        assertEquals(1, calls.get());
    }

    @Test
    @DisplayName("Demo 4 - composition with CompletableFuture")
    void demo_composition() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool = Executors.newFixedThreadPool(2);

        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenReturn(3.0);

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        int result = executor.submit(a, b, BinaryType.ADD, "add")
                .thenApply(m -> m.get(0,0))
                .thenApply(v -> v * 2)
                .get();

        System.out.println("final = " + result);

        assertEquals(6, result);
    }

    private String now() {
        return LocalTime.now().toString();
    }
}