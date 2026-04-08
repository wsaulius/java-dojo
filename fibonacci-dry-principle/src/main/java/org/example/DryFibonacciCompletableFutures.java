package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class DryFibonacciCompletableFutures {

    public record FibResult(int n, long value, long nextValue, String threadName) {}

    public static void main(String[] args) {

        int targetN = 50;

        final CompletableFuture<FibResult> combinedFuture =
                IntStream.range(0, targetN)
                        .boxed()
                        .reduce(
                                // seed future
                                CompletableFuture.completedFuture(
                                        new FibResult(0, 0, 1, Thread.currentThread().getName())
                                ),

                                // accumulator: evolve the future
                                (future, i) ->
                                        future.thenApplyAsync(prev -> {
                                            FibResult next = new FibResult(
                                                    prev.n() + 1,
                                                    prev.nextValue(),
                                                    prev.value() + prev.nextValue(),
                                                    Thread.currentThread().getName()
                                            );

                                            log("Step " + i +
                                                    " -> F(" + next.n() + ")=" + next.value());

                                            return next;
                                        }),

                                // combiner (not really used in sequential stream)
                                (f1, f2) -> f1
                        );

        try {
            final FibResult result = combinedFuture.get();

            System.out.println();
            System.out.println("Final result");
            System.out.println("------------");
            System.out.println("F(" + result.n() + ") = " + result.value());
            System.out.println("F(" + (result.n() + 1) + ") = " + result.nextValue());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println(e.getCause());
        }
    }

    private static void log(String msg) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + msg);
    }
}