package org.example;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class SolidFibonacciGuiceInjectorsUsed {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new FibonacciModule());

        FibonacciApplication application = injector.getInstance(FibonacciApplication.class);

        try {
            application.run(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Application interrupted.");
        } catch (ExecutionException e) {
            System.err.println("Computation failed: " + e.getCause());
        }
    }
}

record FibResult(int n, long value, long nextValue, String threadName) {
}

interface FibonacciStep {
    FibResult next(FibResult current);
}

@Singleton
final class DefaultFibonacciStep implements FibonacciStep {

    @Override
    public FibResult next(FibResult current) {
        return new FibResult(
                current.n() + 1,
                current.nextValue(),
                current.value() + current.nextValue(),
                Thread.currentThread().getName()
        );
    }
}

interface FibonacciAsyncService {
    CompletableFuture<FibResult> calculate(int targetN);
}

@Singleton
final class StreamBasedFibonacciAsyncService implements FibonacciAsyncService {

    private final FibonacciStep fibonacciStep;

    @Inject
    StreamBasedFibonacciAsyncService(FibonacciStep fibonacciStep) {
        this.fibonacciStep = fibonacciStep;
    }

    @Override
    public CompletableFuture<FibResult> calculate(int targetN) {
        validate(targetN);

        CompletableFuture<FibResult> seed =
                CompletableFuture.completedFuture(
                        new FibResult(0, 0, 1, Thread.currentThread().getName())
                );

        return IntStream.range(0, targetN)
                .boxed()
                .reduce(
                        seed,
                        (future, i) -> future.thenApplyAsync(fibonacciStep::next),
                        (left, right) -> left
                );
    }

    private void validate(int targetN) {
        if (targetN < 0) {
            throw new IllegalArgumentException("targetN must be >= 0");
        }
    }
}

interface FibResultPrinter {
    void print(FibResult result);
}

@Singleton
final class ConsoleFibResultPrinter implements FibResultPrinter {

    @Override
    public void print(FibResult result) {
        System.out.println("Final result");
        System.out.println("------------");
        System.out.println("F(" + result.n() + ") = " + result.value());
        System.out.println("F(" + (result.n() + 1) + ") = " + result.nextValue());
        System.out.println("Computed by: " + result.threadName());
    }
}

@Singleton
final class FibonacciApplication {

    private final FibonacciAsyncService fibonacciAsyncService;
    private final FibResultPrinter fibResultPrinter;

    @Inject
    FibonacciApplication(
            FibonacciAsyncService fibonacciAsyncService,
            FibResultPrinter fibResultPrinter
    ) {
        this.fibonacciAsyncService = fibonacciAsyncService;
        this.fibResultPrinter = fibResultPrinter;
    }

    public void run(int targetN) throws ExecutionException, InterruptedException {
        CompletableFuture<FibResult> future = fibonacciAsyncService.calculate(targetN);
        FibResult result = future.get();
        fibResultPrinter.print(result);
    }
}

final class FibonacciModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FibonacciStep.class).to(DefaultFibonacciStep.class);
        bind(FibonacciAsyncService.class).to(StreamBasedFibonacciAsyncService.class);
        bind(FibResultPrinter.class).to(ConsoleFibResultPrinter.class);
    }
}