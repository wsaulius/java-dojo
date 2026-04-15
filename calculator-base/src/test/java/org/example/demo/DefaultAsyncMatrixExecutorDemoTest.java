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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DefaultAsyncMatrixExecutorDemoTest {

    private ThreadPoolExecutor pool;

    @BeforeEach
    void print(TestInfo info) {
        System.out.println("\n==== " + info.getDisplayName() + " ====");
    }

    @BeforeEach
    void setUp() {
        pool = new ThreadPoolExecutor(
                2,
                32,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
    }

    @AfterEach
    void cleanup() {
        if (pool != null) pool.shutdownNow();
    }

    private String now() {
        return LocalTime.now().toString();
    }

    @Test
    @DisplayName("Demo 1 - async non-blocking execution")
    void demo_asyncNonBlocking() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool.setCorePoolSize(2);

        // Mock: simulate delayed async execution (500ms)
        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    System.out.println(now() + " worker started on " + Thread.currentThread().getName());
                    Thread.sleep(500);
                    System.out.println(now() + " worker finished");
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        System.out.println(now() + " submitting async matrix");

        // Submit async matrix computation (non-blocking)
        CompletableFuture<Matrix> future =
                executor.submit(a, b, BinaryType.ADD, "add");

        // Immediately after submit → should NOT be done
        System.out.println(now() + " immediately after submit, done? " + future.isDone());

        // Small delay → still likely running
        Thread.sleep(100);
        System.out.println(now() + " shortly after, still running, done? " + future.isDone());

        // Block here until result is ready
        Matrix result = future.get();

        System.out.println(now() + " after get()");
        System.out.println(now() + " result = " + result.get(0,0));
    }

    @Test
    @DisplayName("Demo 2 - parallel cell execution")
    void demo_parallelCells() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool.setCorePoolSize(4);

        // Each cell logs thread → shows parallel execution
        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    System.out.println(now() + " CELL on " + Thread.currentThread().getName());
                    Thread.sleep(300);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1,2},{3,4}});
        Matrix b = new Matrix(new int[][]{{5,6},{7,8}});

        System.out.println(now() + " submitting matrix");

        // All cells execute in parallel via thread pool
        Matrix result = executor.submit(a, b, BinaryType.ADD, "add").get();

        System.out.println(now() + " matrix done");

        // sanity check
        Assertions.assertEquals(6, result.get(0,0));
    }

    @Test
    @DisplayName("Demo 3 - cache prevents recomputation")
    void demo_cache() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool.setCorePoolSize(2);

        AtomicInteger calls = new AtomicInteger();

        // Count real executions → should only run once due to cache
        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    int c = calls.incrementAndGet();
                    System.out.println(now() + " REAL EXECUTION #" + c);
                    return (double) inv.getArgument(1) + (double) inv.getArgument(2);
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        System.out.println("FIRST CALL");
        executor.submit(a, b, BinaryType.ADD, "add").get();

        System.out.println("SECOND CALL (should be cached)");
        executor.submit(a, b, BinaryType.ADD, "add").get();

        // Should be 1 → second call used cache
        System.out.println("TOTAL EXECUTIONS = " + calls.get());

        Assertions.assertEquals(1, calls.get());
    }

    @Test
    @DisplayName("Demo 4 - CompletableFuture composition (with detailed logging)")
    void demo_composition() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        pool.setCorePoolSize(2);

        // Log when service is actually invoked
        when(service.runBinary(eq(BinaryType.ADD), anyDouble(), anyDouble()))
                .thenAnswer(inv -> {
                    System.out.println(now() + " [SERVICE] runBinary called on " + Thread.currentThread().getName());
                    return 3.0;
                });

        DefaultAsyncCalculationExecutor calc =
                new DefaultAsyncCalculationExecutor(service, pool);

        DefaultAsyncMatrixExecutor executor =
                new DefaultAsyncMatrixExecutor(calc);

        Matrix a = new Matrix(new int[][]{{1}});
        Matrix b = new Matrix(new int[][]{{2}});

        System.out.println(now() + " [MAIN] starting composition on " + Thread.currentThread().getName());

        // Base async computation
        CompletableFuture<Matrix> baseFuture =
                executor.submit(a, b, BinaryType.ADD, "add")
                        .whenComplete((res, ex) -> {
                            System.out.println(now() + " [FUTURE] matrix completed on " + Thread.currentThread().getName());
                        });

        // Chain transformations
        int result = baseFuture
                .thenApply(m -> {
                    System.out.println(now() + " [STAGE 1] extract value on " + Thread.currentThread().getName());
                    return m.get(0,0);
                })
                .thenApply(v -> {
                    System.out.println(now() + " [STAGE 2] multiply on " + Thread.currentThread().getName());
                    return v * 2;
                })
                .whenComplete((res, ex) -> {
                    System.out.println(now() + " [FINAL STAGE] completed on " + Thread.currentThread().getName());
                })
                .get();

        System.out.println(now() + " [MAIN] final result = " + result + " on " + Thread.currentThread().getName());
    }
}