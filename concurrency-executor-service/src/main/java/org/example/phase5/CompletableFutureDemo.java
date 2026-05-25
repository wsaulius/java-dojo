package org.example.phase5;

import java.util.concurrent.CompletableFuture;

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

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        CompletableFuture<String> result =
                fetchUser()
                        .thenCompose(user -> fetchOrders(user));

        System.out.println(result.join());
    }
}
