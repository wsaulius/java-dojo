package org.example.implementations;

import java.util.stream.Stream;
import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;

public class FibonacciSequenceGenerator implements FibonacciOperation<FibonacciSequence> {

    @Override
    public FibonacciSequence apply(int n) {
        if (n <= 0) {
            return new FibonacciSequence(java.util.List.of());
        }

        return new FibonacciSequence(
            Stream
                .iterate(new long[]{0, 1}, arr -> new long[]{arr[1], arr[0] + arr[1]})
                .limit(n)
                .mapToLong(arr -> arr[0])
                .boxed()
                .toList()
        );
    }
}
