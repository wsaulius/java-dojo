package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.enums.*;
import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.execution.DefaultCalculationExecutor;
import org.example.factories.CalculationConsumerResolver;
import org.example.interfaces.AsyncCalculationExecutor;
import org.example.interfaces.CalculationExecutor;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import org.example.modules.*;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static com.google.common.util.concurrent.Futures.getUnchecked;

public class Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Injector injector = Guice.createInjector(new CalculatorApplicationModule());

        CalculationConsumerResolver resolver = injector.getInstance(CalculationConsumerResolver.class);
        CalculationExecutor concurrentExecutor = injector.getInstance(CalculationExecutor.class);
        AsyncCalculationExecutor asyncExecutor = injector.getInstance(AsyncCalculationExecutor.class);

        // Concurrency using Future (no batch)
        List<Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> futureTasks =
                java.util.stream.IntStream.rangeClosed(1, 20)
                        .mapToObj(i -> concurrentExecutor.submitUnaryInt(UnaryIntType.CUBE, i))
                        .toList();

        futureTasks.forEach(f ->
                resolver.unaryInt().accept(getUnchecked(f))
        );

        // Concurrency using Future with batch
        List<Integer> inputs = List.of(2, 3, 4, 5);

        var futures = concurrentExecutor.submitUnaryIntBatch(UnaryIntType.CUBE, inputs);

        for (var future : futures) {
            resolver.unaryInt().accept(future.get());
        }

        // Concurrency using CompletableFuture (no batch)
        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> cubeTask =
                asyncExecutor.submitUnaryInt(UnaryIntType.CUBE, 4);

        CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> sqrtTask =
                asyncExecutor.submitUnaryDouble(UnaryDoubleType.SQRT, 81);

        CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>> factorialTask =
                asyncExecutor.submitUnaryLong(UnaryLongType.FACTORIAL, 10);

        CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> primeTask =
                asyncExecutor.submitUnaryBoolean(UnaryBooleanType.IS_PRIME, 29);

        CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> fibonacciTask =
                asyncExecutor.submitUnaryBigInteger(UnaryBigIntegerType.FIBONACCI, 500);

        CompletableFuture<BinaryCalculationRecord> addTask =
                asyncExecutor.submitBinary(BinaryType.ADD, 10.0, 5.0);

        CompletableFuture<BinaryCalculationRecord> divideTask =
                asyncExecutor.submitBinary(BinaryType.DIVIDE, 100.0, 4.0);

        CompletableFuture.allOf(
                cubeTask,
                sqrtTask,
                factorialTask,
                primeTask,
                fibonacciTask,
                addTask,
                divideTask
        ).join();

        resolver.unaryInt().accept(cubeTask.join());
        resolver.unaryDouble().accept(sqrtTask.join());
        resolver.unaryLong().accept(factorialTask.join());
        resolver.unaryBoolean().accept(primeTask.join());
        resolver.unaryBigInteger().accept(fibonacciTask.join());
        resolver.binary().accept(addTask.join());
        resolver.binary().accept(divideTask.join());

        concurrentExecutor.shutdown();
        asyncExecutor.shutdown();
    }
}