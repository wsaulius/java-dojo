package org.example.demo;

import org.example.enums.UnaryIntType;
import org.example.execution.DefaultCalculationExecutor;
import org.example.models.UnaryCalculationRecord;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCalculationExecutorDemoTest {

    private ExecutorService executorService;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("RUNNING: " + testInfo.getDisplayName());
        System.out.println("========================================");
    }

    @AfterEach
    void tearDown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Test
    @DisplayName("Demo 1 - Future.get blocks until calculation finishes")
    void demo_blockingGet() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(1);

        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 5)).thenAnswer(invocation -> {
            System.out.println(now() + " worker started: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println(now() + " worker finished: " + Thread.currentThread().getName());
            return 25;
        });

        DefaultCalculationExecutor executor =
                new DefaultCalculationExecutor(calculatorService, executorService);

        long start = System.currentTimeMillis();
        System.out.println(now() + " submitting task");

        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 5);

        System.out.println(now() + " submitted, future done? " + future.isDone());
        System.out.println(now() + " calling get() and waiting...");

        UnaryCalculationRecord<UnaryIntType, Integer, Integer> result = future.get();

        long duration = System.currentTimeMillis() - start;

        System.out.println(now() + " get() returned");
        System.out.println(now() + " result = " + result.result());
        System.out.println(now() + " total duration ms = " + duration);

        assertTrue(duration >= 1000);
        assertEquals(25, result.result());
    }

    @Test
    @DisplayName("Demo 2 - independent tasks run in parallel")
    void demo_parallelExecution() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(2);

        CountDownLatch releaseLatch = new CountDownLatch(1);

        when(calculatorService.runUnaryInt(eq(UnaryIntType.SQUARE), anyInt())).thenAnswer(invocation -> {
            Integer input = invocation.getArgument(1);
            System.out.println(now() + " task " + input + " started on " + Thread.currentThread().getName());
            releaseLatch.await(2, TimeUnit.SECONDS);
            System.out.println(now() + " task " + input + " finishing on " + Thread.currentThread().getName());
            return input * input;
        });

        DefaultCalculationExecutor executor =
                new DefaultCalculationExecutor(calculatorService, executorService);

        System.out.println(now() + " submitting two tasks");

        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> first =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 2);

        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> second =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 3);

        Thread.sleep(300);

        System.out.println(now() + " first done? " + first.isDone());
        System.out.println(now() + " second done? " + second.isDone());
        System.out.println(now() + " releasing both tasks");

        releaseLatch.countDown();

        System.out.println(now() + " first result = " + first.get().result());
        System.out.println(now() + " second result = " + second.get().result());

        assertEquals(4, first.get().result());
        assertEquals(9, second.get().result());
    }

    @Test
    @DisplayName("Demo 3 - one failed task does not stop another")
    void demo_failureIsolation() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(2);

        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 1)).thenAnswer(invocation -> {
            System.out.println(now() + " failing task started on " + Thread.currentThread().getName());
            throw new IllegalStateException("boom");
        });

        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 2)).thenAnswer(invocation -> {
            System.out.println(now() + " successful task started on " + Thread.currentThread().getName());
            return 4;
        });

        DefaultCalculationExecutor executor =
                new DefaultCalculationExecutor(calculatorService, executorService);

        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> failingFuture =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 1);

        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> successFuture =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 2);

        try {
            failingFuture.get();
            fail("Expected ExecutionException");
        } catch (ExecutionException e) {
            System.out.println(now() + " failing future threw: " + e.getCause());
            assertInstanceOf(IllegalStateException.class, e.getCause());
        }

        UnaryCalculationRecord<UnaryIntType, Integer, Integer> success = successFuture.get();
        System.out.println(now() + " successful future still completed with result = " + success.result());

        assertEquals(4, success.result());
    }

    @Test
    @DisplayName("Demo 4 - shutdown rejects new submissions")
    void demo_shutdownStopsNewSubmissions() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(1);

        DefaultCalculationExecutor executor =
                new DefaultCalculationExecutor(calculatorService, executorService);

        System.out.println(now() + " shutting down executor");
        executor.shutdown();
        System.out.println(now() + " shutdown completed, submitting new task");

        RejectedExecutionException exception = assertThrows(
                RejectedExecutionException.class,
                () -> executor.submitUnaryInt(UnaryIntType.SQUARE, 2)
        );

        System.out.println(now() + " submission rejected: " + exception.getClass().getSimpleName());
    }

    private String now() {
        return LocalTime.now().toString();
    }
}