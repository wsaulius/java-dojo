package org.example;

import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;

public class FibonacciDemoRunner {

    private final FibonacciOperation<FibonacciSequence> sequenceOperation;
    private final FibonacciOperation<Long> numberOperation;

    public FibonacciDemoRunner(FibonacciOperation<FibonacciSequence> sequenceOperation,
                               FibonacciOperation<Long> numberOperation) {
        this.sequenceOperation = sequenceOperation;
        this.numberOperation = numberOperation;
    }

    public void run(int n) {
        FibonacciSequence sequence = sequenceOperation.apply(n);
        Long nth = numberOperation.apply(n);

        System.out.println("Requested n = " + n);
        System.out.println("First " + n + " Fibonacci numbers: " + sequence.getValues());
        System.out.println("Fibonacci number at position " + n + ": " + nth);
    }
}
