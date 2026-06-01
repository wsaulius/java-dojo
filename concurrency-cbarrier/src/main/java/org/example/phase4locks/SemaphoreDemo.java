package org.example.phase4locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    //Limiting access to N threads for a specific resource to prevent overloading, commonly used for database connections
    public static void main(String[] args) {

        //Ticket limit
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i <= 10; i++) {

            int id = i;

            executor.submit(() -> {
                try {
                    System.out.println("Task " + id + " waiting for connection");
                    //Take 1 ticket
                    semaphore.acquire();

                    System.out.println("Task " + id + " connected on " + Thread.currentThread().getName());
                    //Stimulating work
                    Thread.sleep(3000);
                    System.out.println("Task " + id + " finished work");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    //Return a ticket no matter if try/catch failed or succeeded
                    semaphore.release();
                    System.out.println("Task " + id + " released DB connection");
                }
            });
        }
        executor.shutdown();
    }
}
