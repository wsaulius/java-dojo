package org.example.demo;

import org.example.enums.BinaryType;
import org.example.enums.UnaryIntType;
import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalTime;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DefaultAsyncCalculationExecutorDemoTest {

    private ExecutorService executorService;

    @AfterEach
    void tearDown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("RUNNING: " + testInfo.getDisplayName());
        System.out.println("========================================");
    }

    @Test
    @DisplayName("Demo 1 - CompletableFuture is non-blocking and runs asynchronously")
    void demo_asyncNonBlocking() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(1);

        when(service.runUnaryInt(UnaryIntType.SQUARE, 5)).thenAnswer(inv -> {
            System.out.println(now() + " worker started: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println(now() + " worker finished: " + Thread.currentThread().getName());
            return 25;
        });

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(service, executorService);

        long start = System.currentTimeMillis();

        System.out.println(now() + " submitting async task");
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 5);

        System.out.println(now() + " immediately after submit, done? " + future.isDone());

        UnaryCalculationRecord<?, ?, ?> result = future.get();

        long duration = System.currentTimeMillis() - start;

        System.out.println(now() + " completed with result = " + result.result());
        System.out.println(now() + " duration ms = " + duration);

        assertTrue(duration >= 1000);
        assertEquals(25, result.result());
    }

    @Test
    @DisplayName("Demo 2 - multiple async tasks run in parallel")
    void demo_parallelAsync() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(1);

        when(service.runUnaryInt(eq(UnaryIntType.SQUARE), anyInt())).thenAnswer(inv -> {
            Integer input = inv.getArgument(1);
            System.out.println(now() + " task " + input + " started on " + Thread.currentThread().getName());
            latch.await(2, TimeUnit.SECONDS);
            System.out.println(now() + " task " + input + " finishing");
            return input * input;
        });

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(service, executorService);

        CompletableFuture<?> f1 = executor.submitUnaryInt(UnaryIntType.SQUARE, 2);
        CompletableFuture<?> f2 = executor.submitUnaryInt(UnaryIntType.SQUARE, 3);

        Thread.sleep(300);

        System.out.println(now() + " f1 done? " + f1.isDone());
        System.out.println(now() + " f2 done? " + f2.isDone());

        latch.countDown();

        assertEquals(4, ((UnaryCalculationRecord<?, ?, ?>) f1.get()).result());
        assertEquals(9, ((UnaryCalculationRecord<?, ?, ?>) f2.get()).result());
    }

    @Test
    @DisplayName("Demo 3 - async failure propagates through CompletableFuture")
    void demo_failurePropagation() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(2);

        when(service.runUnaryInt(UnaryIntType.SQUARE, 1))
                .thenThrow(new IllegalStateException("boom"));

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(service, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 1);

        ExecutionException ex = assertThrows(ExecutionException.class, future::get);

        System.out.println(now() + " async failure: " + ex.getCause());

        assertInstanceOf(IllegalStateException.class, ex.getCause());
    }

    @Test
    @DisplayName("Demo 4 - CompletableFuture chaining (thenApply)")
    void demo_composition() throws Exception {
        CalculatorService service = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(1);

        when(service.runBinary(BinaryType.ADD, 10.0, 5.0)).thenReturn(15.0);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(service, executorService);

        CompletableFuture<Integer> chained =
                executor.submitBinary(BinaryType.ADD, 10.0, 5.0)
                        .thenApply(BinaryCalculationRecord::result)
                        .thenApply(Double::intValue)
                        .thenApply(result -> {
                            System.out.println(now() + " chained result = " + result);
                            return result * 2;
                        });

        int finalResult = chained.get();

        System.out.println(now() + " final result after chaining = " + finalResult);

        assertEquals(30, finalResult);
    }

    private String now() {
        return LocalTime.now().toString();
    }
}