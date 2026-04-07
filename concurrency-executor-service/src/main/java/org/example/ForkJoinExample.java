package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;

public class ForkJoinExample {

    private static final int TASK_COUNT = 100;
    private static final int THRESHOLD = 10; // split tasks when size > 10

    public static void main(String[] args) {

        // 1. Create a ForkJoinPool with 10 threads
        ForkJoinPool pool = new ForkJoinPool(10);

        // 2. Prepare list of "tasks" (random numbers)
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < TASK_COUNT; i++) {
            numbers.add(ThreadLocalRandom.current().nextInt(1, 100));
        }

        // 3. Create root ForkJoin task
        SquareSumTask rootTask = new SquareSumTask(numbers, 0, numbers.size());

        // 4. Execute task in the pool
        int totalSum = pool.invoke(rootTask);

        // 5. Shutdown pool
        pool.shutdown();

        System.out.println("Total sum of squares = " + totalSum);
    }

    // 6. RecursiveTask to calculate sum of squares
    static class SquareSumTask extends RecursiveTask<Integer> {

        private final List<Integer> numbers;
        private final int start, end;

        SquareSumTask(List<Integer> numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int length = end - start;

            // Base case: small enough to compute directly
            if (length <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    int value = numbers.get(i);
                    int square = value * value;
                    sum += square;
                    System.out.println(Thread.currentThread().getName() +
                            " computing index " + i + " | value=" + value + " | square=" + square);
                }
                return sum;
            }

            // Recursive case: split into two halves
            int mid = start + length / 2;
            SquareSumTask leftTask = new SquareSumTask(numbers, start, mid);
            SquareSumTask rightTask = new SquareSumTask(numbers, mid, end);

            // Fork left task (runs asynchronously)
            leftTask.fork();

            // Compute right task synchronously
            int rightResult = rightTask.compute();

            // Join left task result
            int leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}