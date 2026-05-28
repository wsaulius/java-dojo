package org.example.phase4.fork;

import java.util.concurrent.ForkJoinPool;

public class SingleVsForkSum {

    public static long sum(long start, long end) {

        long result = 0;

        for (long i = start; i <= end; i++) {
            result += i;
        }

        return result;
    }

    public static void main(String[] args) {

        //Single thread
        long startTime = System.currentTimeMillis();

        long result = sum(1, 200_000_000);

        long endTime = System.currentTimeMillis();

        System.out.println("Result: " + result);
        System.out.println("Single thread time: " + (endTime - startTime) + " ms");

        //ForkJoin
        ForkJoinPool pool = new ForkJoinPool();

        long forkStartTime = System.currentTimeMillis();

        ForkSum task = new ForkSum(1, 200_000_000);

        long forkResult = pool.invoke(task);

        long forkEndTime = System.currentTimeMillis();

        System.out.println("Result: " + forkResult);
        System.out.println("ForkJoin time: " + (forkEndTime - forkStartTime) + " ms");
    }
}
