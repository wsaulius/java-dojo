package org.example.implementations;

import java.util.stream.Stream;
import org.example.interfaces.FibonacciOperation;

public class FibonacciNumberCalculator implements FibonacciOperation<Long> {

    @Override
    public Long apply(int n) {
        if (n <= 0) {
            return 0L;
        }

        return Stream
            .iterate(new long[]{0, 1}, arr -> new long[]{arr[1], arr[0] + arr[1]})
            .limit(n)
            .map(arr -> arr[0])
            .reduce((a, b) -> b)
            .orElse(0L);
    }
}
