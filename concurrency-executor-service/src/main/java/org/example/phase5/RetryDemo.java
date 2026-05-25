package org.example.phase5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryDemo {

    static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        //fetchData()
        //   ↓ fail
        //handle()
        //   ↓ retryFetch()
        //   ↓ fetchData()
        //   ↓ success
        //thenCompose unwrap
        //   ↓
        //final result
        CompletableFuture<String> result = retryFetch(3);
        System.out.println(result.join());
    }

    static CompletableFuture<String> fetchData() {
        return CompletableFuture.supplyAsync(() -> {

            int attempt = counter.incrementAndGet();
            System.out.println("Attempt: " + attempt);

            if (attempt < 3) {
                throw new RuntimeException("Temporary failure");
            }

            return "SUCCESS";
        });
    }

    static CompletableFuture<String> retryFetch(int retries) {
        return fetchData()
                .handle((result, ex) -> {

                    if (ex == null) {
                        return CompletableFuture.completedFuture(result);
                    }

                    System.out.println("Failed: " + ex.getMessage());

                    if (retries > 0) {
                        System.out.println("Retrying... remaining: " + retries);

                        return retryFetch(retries - 1);
                    }

                    return CompletableFuture.completedFuture("Fallback value");
                })
                .thenCompose(future -> future);
    }
}
