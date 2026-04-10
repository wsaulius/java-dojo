package org.example.execution;

import com.google.inject.Singleton;
import jakarta.inject.Inject;
import org.example.enums.BinaryType;
import org.example.enums.UnaryBigIntegerType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.CalcPool;
import org.example.services.CalculatorService;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Singleton
public final class DefaultAsyncCalculationExecutor implements AsyncCalculationExecutor {

    private final CalculatorService calculatorService;
    private final ExecutorService executorService;

    @Inject
    public DefaultAsyncCalculationExecutor(
            CalculatorService calculatorService,
            @CalcPool ExecutorService executorService
    ) {
        this.calculatorService = calculatorService;
        this.executorService = executorService;
    }

    @Override
    public CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
            UnaryIntType type,
            Integer input
    ) {
        return submitUnary(
                type,
                input,
                () -> calculatorService.runUnaryInt(type, input)
        );
    }

    @Override
    public CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
            UnaryDoubleType type,
            Integer input
    ) {
        return submitUnary(
                type,
                input,
                () -> calculatorService.runUnaryDouble(type, input)
        );
    }

    @Override
    public CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
            UnaryLongType type,
            Integer input
    ) {
        return submitUnary(
                type,
                input,
                () -> calculatorService.runUnaryLong(type, input)
        );
    }

    @Override
    public CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
            UnaryBooleanType type,
            Integer input
    ) {
        return submitUnary(
                type,
                input,
                () -> calculatorService.runUnaryBoolean(type, input)
        );
    }

    @Override
    public CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
            UnaryBigIntegerType type,
            Integer input
    ) {
        return submitUnary(
                type,
                input,
                () -> calculatorService.runUnaryBigInteger(type, input)
        );
    }

    @Override
    public List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryInt(type, input))
                .toList();
    }

    @Override
    public List<CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryDouble(type, input))
                .toList();
    }

    @Override
    public List<CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryLong(type, input))
                .toList();
    }

    @Override
    public List<CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryBoolean(type, input))
                .toList();
    }

    @Override
    public List<CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryBigInteger(type, input))
                .toList();
    }

    @Override
    public CompletableFuture<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    ) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start " + type + " " + left + ", " + right + " -> " + Thread.currentThread().getName());

            BinaryCalculationRecord record =
                    new BinaryCalculationRecord(
                            type,
                            left,
                            right,
                            calculatorService.runBinary(type, left, right)
                    );

            System.out.println("done " + type + " " + left + ", " + right + " -> " + Thread.currentThread().getName());
            return record;
        }, executorService);
    }

    private <E extends Enum<E>, I, R> CompletableFuture<UnaryCalculationRecord<E, I, R>> submitUnary(
            E type,
            I input,
            Supplier<R> calculation
    ) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start " + type + " " + input + " -> " + Thread.currentThread().getName());

            UnaryCalculationRecord<E, I, R> record =
                    new UnaryCalculationRecord<>(
                            type,
                            input,
                            calculation.get()
                    );

            System.out.println("done " + type + " " + input + " -> " + Thread.currentThread().getName());
            return record;
        }, executorService);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}