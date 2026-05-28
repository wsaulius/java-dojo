package org.example.phase4.fork;

import java.util.concurrent.RecursiveTask;

public class ForkSum extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10_000;

    private final long start;
    private final long end;

    public ForkSum(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long length = end - start;

        // base case
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }

        // split task
        long mid = (start + end) / 2;

        ForkSum left = new ForkSum(start, mid);
        ForkSum right = new ForkSum(mid + 1, end);

        left.fork(); // run asynchronously
        long rightResult = right.compute(); // run directly
        long leftResult = left.join(); // wait for forked task

        return leftResult + rightResult;
    }
}
