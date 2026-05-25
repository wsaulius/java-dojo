package org.example.phase5;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class FullDemo {
    //1. Start User API (async + retry)
    //2. Start Payment API (async + retry)
    //3. Add timeout + fallback to both
    //4. Run both in parallel
    //5. Combine results into dashboard string
    //6. Print result

    // Simulate unstable APIs
    static AtomicInteger userAttempts = new AtomicInteger();
    static AtomicInteger paymentAttempts = new AtomicInteger();

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        // 1. Start async operations WITH retry
        CompletableFuture<String> userFuture =
                retry(FullDemo::fetchUser, 3);

        CompletableFuture<String> paymentFuture =
                retry(FullDemo::fetchPayment, 3);

        // 2. Add timeout + fallback
        CompletableFuture<String> safeUser =
                withTimeout(userFuture, 2, TimeUnit.SECONDS, "UNKNOWN_USER");

        CompletableFuture<String> safePayment =
                withTimeout(paymentFuture, 2, TimeUnit.SECONDS, "NO_PAYMENT");

        // 3. Combine results in parallel
        CompletableFuture<String> dashboard =
                safeUser.thenCombine(safePayment,
                        (user, payment) ->
                                "Dashboard -> User: " + user + ", Payment: " + payment
                );

        // 4. Get final result
        System.out.println(dashboard.join());

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) + " ms");
    }

    // -------------------------
    // Simulated unstable APIs
    // -------------------------

    static CompletableFuture<String> fetchUser() {
        return CompletableFuture.supplyAsync(() -> {

            int attempt = userAttempts.incrementAndGet();
            System.out.println("User API attempt: " + attempt);

            if (attempt < 2) {
                throw new RuntimeException("User service temporarily down");
            }

            sleep(500); // simulate delay
            return "John";
        });
    }

    static CompletableFuture<String> fetchPayment() {
        return CompletableFuture.supplyAsync(() -> {

            int attempt = paymentAttempts.incrementAndGet();
            System.out.println("Payment API attempt: " + attempt);

            if (attempt < 2) {
                throw new RuntimeException("Payment service timeout");
            }

            sleep(500); // simulate delay
            return "VISA";
        });
    }

    // -------------------------
    // Retry (fully async style)
    // -------------------------

    static <T> CompletableFuture<T> retry(Supplier<CompletableFuture<T>> task, int retries) {
        return task.get()
                .handle((result, ex) -> {
                    if (ex == null) {
                        return CompletableFuture.completedFuture(result);
                    }

                    System.out.println("Error: " + ex.getMessage());

                    if (retries > 0) {
                        System.out.println("Retrying... remaining: " + retries);

                        return retry(task, retries - 1);
                    }

                    return CompletableFuture.<T>completedFuture(null);
                })
                .thenCompose(future -> future);
    }

    // -------------------------
    // Timeout + fallback
    // -------------------------

    static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit, T fallback) {
        return future
                .orTimeout(timeout, unit)
                .exceptionally(ex -> {
                    System.out.println("Timeout/failure: " + ex.getMessage());
                    return fallback;
                });
    }

    // -------------------------
    // Helper sleep
    // -------------------------

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}