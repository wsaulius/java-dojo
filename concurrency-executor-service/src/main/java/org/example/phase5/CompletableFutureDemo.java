package org.example.phase5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    static CompletableFuture<String> fetchUser() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "John";
        });
    }

    static CompletableFuture<String> fetchOrders(String user) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Orders for " + user;
        });
    }

    static CompletableFuture<String> fetchPayment() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Visa Card";
        });
    }

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        //Chaining async API calls
        CompletableFuture<String> result =
                fetchUser()
                        .thenCompose(user -> fetchOrders(user));

        System.out.println(result.join());

        //Parallel async
        long start = System.currentTimeMillis();

        CompletableFuture<String> userFuture = fetchUser();
        CompletableFuture<String> paymentFuture = fetchPayment();

        CompletableFuture<String> combined =
                userFuture.thenCombine(paymentFuture,
                        (user, payment) ->
                                user + " paid with " + payment
                );

        System.out.println(combined.join());

        long end = System.currentTimeMillis();

        System.out.println("Time: " + (end - start) + " ms");

        //Exception handling
        //exceptionally() - recover from error
        //Error
        //↓
        //recover
        //↓
        //pipeline continues successfully
        CompletableFuture<String> future =
                CompletableFuture.<String>supplyAsync(() -> {
                            throw new RuntimeException("API failed");
                        })
                        .exceptionally(ex -> {
                            System.out.println("Error: " + ex.getMessage());
                            return "Fallback value";
                        });

        System.out.println(future.join());

        //handle() - handle success or failure
        //Success OR Error
        //↓
        //transform into new result
        CompletableFuture<String> futureHandle =
                CompletableFuture.<String>supplyAsync(() -> {
                            throw new RuntimeException("Boom");
                        })
                        .handle((value, ex) -> {
                            if (ex != null) {
                                return "Recovered";
                            }

                            return value;
                        });

        System.out.println(futureHandle.join());

        //whenCompleted() - observe result/error without changing it
        //Observe result/error
        //↓
        //do side effect
        //↓
        //original outcome continues
//        CompletableFuture<String> futureWhen =
//                CompletableFuture.<String>supplyAsync(() -> {
//                            throw new RuntimeException("Failure");
//                        })
//                        .whenComplete((value, ex) -> {
//
//                            if (ex != null) {
//                                System.out.println("Logging error: " + ex.getMessage());
//                            }
//                        });
//
//        System.out.println(futureWhen.join());

        //timeouts
        //orTimeout() - fail with exception
        //Task too slow
        //↓
        //future marked failed
        //↓
        //pipeline moves to error handling
        CompletableFuture<String> futureTimeout =
                CompletableFuture.<String>supplyAsync(() -> {
                            sleep(5000);
                            return "API Response";
                        })
                        .orTimeout(2, TimeUnit.SECONDS);

        try {
            System.out.println(futureTimeout.join());
        } catch (Exception e) {
            System.out.println("Timeout happened!");
            System.out.println(e.getMessage());
        }

        //completeOnTimeout() - return fallback value
        //Timeout
        //↓
        //return fallback value
        CompletableFuture<String> futureComplete =
                CompletableFuture.<String>supplyAsync(() -> {
                            sleep(5000);
                            return "Real API Response";
                        })
                        .completeOnTimeout("Default Response", 2, TimeUnit.SECONDS);

        System.out.println(futureComplete.join());

        //combining orTimeout() with exceptionally()
        CompletableFuture<String> futureCombined =
                CompletableFuture.<String>supplyAsync(() -> {
                            sleep(5000);
                            return "Response";
                        })
                        .orTimeout(2, TimeUnit.SECONDS)
                        .exceptionally(ex -> "Fallback after timeout");

        System.out.println(futureCombined.join());

    }
}

