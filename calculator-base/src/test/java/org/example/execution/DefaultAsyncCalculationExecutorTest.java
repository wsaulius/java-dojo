package org.example.execution;

import org.example.enums.*;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultAsyncCalculationExecutorTest {

    private ExecutorService executorService;

    @AfterEach
    void tearDown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Test
    void shouldExecuteMultipleTasksConcurrently() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(3);

        CountDownLatch startedLatch = new CountDownLatch(3);
        CountDownLatch releaseLatch = new CountDownLatch(1);
        AtomicInteger running = new AtomicInteger();
        AtomicInteger maxRunning = new AtomicInteger();

        when(calculatorService.runUnaryInt(eq(UnaryIntType.SQUARE), anyInt())).thenAnswer(invocation -> {
            int current = running.incrementAndGet();
            maxRunning.updateAndGet(previous -> Math.max(previous, current));
            startedLatch.countDown();

            try {
                assertTrue(releaseLatch.await(2, TimeUnit.SECONDS));
                Integer input = invocation.getArgument(1);
                return input * input;
            } finally {
                running.decrementAndGet();
            }
        });

        DefaultAsyncCalculationExecutor executor = new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> first = executor.submitUnaryInt(UnaryIntType.SQUARE, 2);
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> second = executor.submitUnaryInt(UnaryIntType.SQUARE, 3);
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> third = executor.submitUnaryInt(UnaryIntType.SQUARE, 4);

        assertTrue(startedLatch.await(1, TimeUnit.SECONDS));
        assertTrue(maxRunning.get() >= 2);
        assertFalse(first.isDone());
        assertFalse(second.isDone());
        assertFalse(third.isDone());

        releaseLatch.countDown();

        assertEquals(4, first.get(1, TimeUnit.SECONDS).result());
        assertEquals(9, second.get(1, TimeUnit.SECONDS).result());
        assertEquals(16, third.get(1, TimeUnit.SECONDS).result());
    }

    @Test
    void shouldCompleteOtherTasksWhenOneFails() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(3);

        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 1)).thenThrow(new IllegalStateException("boom"));
        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 2)).thenReturn(4);
        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 3)).thenReturn(9);

        DefaultAsyncCalculationExecutor executor = new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> failingFuture = executor.submitUnaryInt(UnaryIntType.SQUARE, 1);
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> secondFuture = executor.submitUnaryInt(UnaryIntType.SQUARE, 2);
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> thirdFuture = executor.submitUnaryInt(UnaryIntType.SQUARE, 3);

        ExecutionException executionException = assertThrows(
                ExecutionException.class,
                () -> failingFuture.get(1, TimeUnit.SECONDS)
        );

        assertInstanceOf(IllegalStateException.class, executionException.getCause());
        assertEquals("boom", executionException.getCause().getMessage());
        assertEquals(4, secondFuture.get(1, TimeUnit.SECONDS).result());
        assertEquals(9, thirdFuture.get(1, TimeUnit.SECONDS).result());

        verify(calculatorService).runUnaryInt(UnaryIntType.SQUARE, 1);
        verify(calculatorService).runUnaryInt(UnaryIntType.SQUARE, 2);
        verify(calculatorService).runUnaryInt(UnaryIntType.SQUARE, 3);
    }

    @Test
    void shouldPreserveCorrectResultsAcrossConcurrentTasks() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(4);

        when(calculatorService.runUnaryInt(eq(UnaryIntType.SQUARE), anyInt())).thenAnswer(invocation -> {
            Integer input = invocation.getArgument(1);
            Thread.sleep((10 - input) * 5L);
            return input * input;
        });

        DefaultAsyncCalculationExecutor executor = new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        List<Integer> inputs = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> futures = new ArrayList<>();

        for (Integer input : inputs) {
            futures.add(executor.submitUnaryInt(UnaryIntType.SQUARE, input));
        }

        for (int i = 0; i < inputs.size(); i++) {
            int expectedInput = inputs.get(i);
            UnaryCalculationRecord<UnaryIntType, Integer, Integer> record = futures.get(i).get(1, TimeUnit.SECONDS);
            assertEquals(UnaryIntType.SQUARE, record.operation());
            assertEquals(expectedInput, record.input());
            assertEquals(expectedInput * expectedInput, record.result());
        }
    }

    @Test
    void shouldHandleBatchExecutionConcurrently() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newFixedThreadPool(4);

        CountDownLatch startedLatch = new CountDownLatch(4);
        CountDownLatch releaseLatch = new CountDownLatch(1);

        when(calculatorService.runUnaryInt(eq(UnaryIntType.SQUARE), anyInt())).thenAnswer(invocation -> {
            startedLatch.countDown();
            assertTrue(releaseLatch.await(2, TimeUnit.SECONDS));
            Integer input = invocation.getArgument(1);
            return input * input;
        });

        DefaultAsyncCalculationExecutor executor = new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        List<Integer> inputs = List.of(2, 3, 4, 5);
        List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> futures =
                executor.submitUnaryIntBatch(UnaryIntType.SQUARE, inputs);

        assertEquals(inputs.size(), futures.size());
        assertTrue(startedLatch.await(1, TimeUnit.SECONDS));
        assertTrue(futures.stream().noneMatch(CompletableFuture::isDone));

        releaseLatch.countDown();

        for (int i = 0; i < inputs.size(); i++) {
            UnaryCalculationRecord<UnaryIntType, Integer, Integer> record = futures.get(i).get(1, TimeUnit.SECONDS);
            int input = inputs.get(i);
            assertEquals(UnaryIntType.SQUARE, record.operation());
            assertEquals(input, record.input());
            assertEquals(input * input, record.result());
        }
    }

    @Test
    void shouldReturnCompletableFutureImmediately() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        CountDownLatch enteredCalculation = new CountDownLatch(1);
        CountDownLatch releaseLatch = new CountDownLatch(1);

        when(calculatorService.runUnaryInt(UnaryIntType.SQUARE, 7)).thenAnswer(invocation -> {
            enteredCalculation.countDown();
            assertTrue(releaseLatch.await(2, TimeUnit.SECONDS));
            return 49;
        });

        DefaultAsyncCalculationExecutor executor = new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        long startNanos = System.nanoTime();
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                executor.submitUnaryInt(UnaryIntType.SQUARE, 7);
        long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);

        assertNotNull(future);
        assertTrue(elapsedMillis < 100);
        assertTrue(enteredCalculation.await(1, TimeUnit.SECONDS));
        assertFalse(future.isDone());

        releaseLatch.countDown();

        UnaryCalculationRecord<UnaryIntType, Integer, Integer> record = future.get(1, TimeUnit.SECONDS);
        assertEquals(7, record.input());
        assertEquals(49, record.result());
    }

    @Test
    void shouldSubmitUnaryInt() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryIntType type = UnaryIntType.values()[0];
        when(calculatorService.runUnaryInt(type, 5)).thenReturn(25);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                executor.submitUnaryInt(type, 5);

        UnaryCalculationRecord<UnaryIntType, Integer, Integer> record = future.join();

        assertEquals(type, record.operation());
        assertEquals(5, record.input());
        assertEquals(25, record.result());

        verify(calculatorService).runUnaryInt(type, 5);
    }

    @Test
    void shouldSubmitUnaryDouble() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryDoubleType type = UnaryDoubleType.values()[0];
        when(calculatorService.runUnaryDouble(type, 4)).thenReturn(2.5);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> future =
                executor.submitUnaryDouble(type, 4);

        UnaryCalculationRecord<UnaryDoubleType, Integer, Double> record = future.join();

        assertEquals(type, record.operation());
        assertEquals(4, record.input());
        assertEquals(2.5, record.result());

        verify(calculatorService).runUnaryDouble(type, 4);
    }

    @Test
    void shouldSubmitUnaryLong() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryLongType type = UnaryLongType.values()[0];
        when(calculatorService.runUnaryLong(type, 6)).thenReturn(720L);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>> future =
                executor.submitUnaryLong(type, 6);

        UnaryCalculationRecord<UnaryLongType, Integer, Long> record = future.join();

        assertEquals(type, record.operation());
        assertEquals(6, record.input());
        assertEquals(720L, record.result());

        verify(calculatorService).runUnaryLong(type, 6);
    }

    @Test
    void shouldSubmitUnaryBoolean() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryBooleanType type = UnaryBooleanType.values()[0];
        when(calculatorService.runUnaryBoolean(type, 8)).thenReturn(true);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> future =
                executor.submitUnaryBoolean(type, 8);

        UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean> record = future.join();

        assertEquals(type, record.operation());
        assertEquals(8, record.input());
        assertTrue(record.result());

        verify(calculatorService).runUnaryBoolean(type, 8);
    }

    @Test
    void shouldSubmitUnaryBigInteger() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryBigIntegerType type = UnaryBigIntegerType.values()[0];
        when(calculatorService.runUnaryBigInteger(type, 10)).thenReturn(BigInteger.valueOf(55));

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> future =
                executor.submitUnaryBigInteger(type, 10);

        UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger> record = future.join();

        assertEquals(type, record.operation());
        assertEquals(10, record.input());
        assertEquals(BigInteger.valueOf(55), record.result());

        verify(calculatorService).runUnaryBigInteger(type, 10);
    }

    @Test
    void shouldSubmitUnaryIntBatch() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        UnaryIntType type = UnaryIntType.values()[0];
        when(calculatorService.runUnaryInt(type, 1)).thenReturn(1);
        when(calculatorService.runUnaryInt(type, 2)).thenReturn(4);
        when(calculatorService.runUnaryInt(type, 3)).thenReturn(9);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> futures =
                executor.submitUnaryIntBatch(type, List.of(1, 2, 3));

        assertEquals(3, futures.size());
        assertEquals(1, futures.get(0).join().result());
        assertEquals(4, futures.get(1).join().result());
        assertEquals(9, futures.get(2).join().result());

        verify(calculatorService).runUnaryInt(type, 1);
        verify(calculatorService).runUnaryInt(type, 2);
        verify(calculatorService).runUnaryInt(type, 3);
    }

    @Test
    void shouldSubmitBinary() {
        CalculatorService calculatorService = mock(CalculatorService.class);
        executorService = Executors.newSingleThreadExecutor();

        BinaryType type = BinaryType.values()[0];
        when(calculatorService.runBinary(type, 10.0, 5.0)).thenReturn(15.0);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        CompletableFuture<BinaryCalculationRecord> future =
                executor.submitBinary(type, 10.0, 5.0);

        BinaryCalculationRecord record = future.join();

        assertEquals(type, record.operation());
        assertEquals(10.0, record.left());
        assertEquals(5.0, record.right());
        assertEquals(15.0, record.result());

        verify(calculatorService).runBinary(type, 10.0, 5.0);
    }

    @Test
    void shouldShutdownExecutorService() throws InterruptedException {
        CalculatorService calculatorService = mock(CalculatorService.class);
        ExecutorService executorService = mock(ExecutorService.class);

        when(executorService.awaitTermination(10, TimeUnit.SECONDS))
                .thenReturn(true);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        executor.shutdown();

        verify(executorService).shutdown();
        verify(executorService).awaitTermination(10, TimeUnit.SECONDS);
        verify(executorService, never()).shutdownNow();
    }

    @Test
    void shouldForceShutdownWhenExecutorDoesNotTerminate() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        ExecutorService executorService = mock(ExecutorService.class);
        when(executorService.awaitTermination(10, TimeUnit.SECONDS)).thenReturn(false);

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        executor.shutdown();

        verify(executorService).shutdown();
        verify(executorService).awaitTermination(10, TimeUnit.SECONDS);
        verify(executorService).shutdownNow();
    }

    @Test
    void shouldForceShutdownWhenInterrupted() throws Exception {
        CalculatorService calculatorService = mock(CalculatorService.class);
        ExecutorService executorService = mock(ExecutorService.class);
        when(executorService.awaitTermination(10, TimeUnit.SECONDS))
                .thenThrow(new InterruptedException());

        DefaultAsyncCalculationExecutor executor =
                new DefaultAsyncCalculationExecutor(calculatorService, executorService);

        assertDoesNotThrow(executor::shutdown);

        verify(executorService).shutdown();
        verify(executorService).awaitTermination(10, TimeUnit.SECONDS);
        verify(executorService).shutdownNow();
        assertTrue(Thread.currentThread().isInterrupted());
        assertTrue(Thread.interrupted());
    }
}