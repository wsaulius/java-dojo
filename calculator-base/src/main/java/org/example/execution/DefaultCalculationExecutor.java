package org.example.execution;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.enums.UnaryBigIntegerType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;
import org.example.interfaces.CalculationExecutor;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.services.CalculatorService;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public final class DefaultCalculationExecutor implements CalculationExecutor {

    private final CalculatorService calculatorService;
    private final ExecutorService executorService;

    @Inject
    public DefaultCalculationExecutor(
            CalculatorService calculatorService,
            ExecutorService executorService
    ) {
        this.calculatorService = calculatorService;
        this.executorService = executorService;
    }

    @Override
    public Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
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
    public Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
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
    public Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
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
    public Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
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
    public Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
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
    public List<Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryInt(type, input))
                .toList();
    }

    @Override
    public List<Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryDouble(type, input))
                .toList();
    }

    @Override
    public List<Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryLong(type, input))
                .toList();
    }

    @Override
    public List<Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryBoolean(type, input))
                .toList();
    }

    @Override
    public List<Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    ) {
        return inputs.stream()
                .map(input -> submitUnaryBigInteger(type, input))
                .toList();
    }

    @Override
    public Future<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    ) {
        return executorService.submit(() -> {
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
        });
    }

    private <E extends Enum<E>, I, R> Future<UnaryCalculationRecord<E, I, R>> submitUnary(
            E type,
            I input,
            Supplier<R> calculation
    ) {
        return executorService.submit(() -> {
            System.out.println("start " + type + " " + input + " -> " + Thread.currentThread().getName());

            UnaryCalculationRecord<E, I, R> record =
                    new UnaryCalculationRecord<>(
                            type,
                            input,
                            calculation.get()
                    );

            System.out.println("done " + type + " " + input + " -> " + Thread.currentThread().getName());
            return record;
        });
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