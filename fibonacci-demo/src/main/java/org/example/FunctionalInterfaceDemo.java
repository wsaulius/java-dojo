package org.example;

import java.util.Scanner;
import org.example.implementations.FibonacciNumberCalculator;
import org.example.implementations.FibonacciSequenceGenerator;
import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;

public class FunctionalInterfaceDemo {

    public static void main(String[] args) {
        int n = readN(args);

        FibonacciOperation<FibonacciSequence> sequenceOp = new FibonacciSequenceGenerator();
        FibonacciOperation<Long> numberOp = new FibonacciNumberCalculator();

        FibonacciDemoRunner runner = new FibonacciDemoRunner(sequenceOp, numberOp);
        runner.run(n);

        System.out.println();
        System.out.println("Replacing nth-number implementation with a lambda:");

        FibonacciOperation<Long> lambdaNumberOp = value -> {
            if (value <= 0) {
                return 0L;
            }

            return java.util.stream.Stream
                .iterate(new long[]{0, 1}, arr -> new long[]{arr[1], arr[0] + arr[1]})
                .limit(value)
                .map(arr -> arr[0])
                .reduce((a, b) -> b)
                .orElse(0L);
        };

        FibonacciDemoRunner lambdaRunner = new FibonacciDemoRunner(sequenceOp, lambdaNumberOp);
        lambdaRunner.run(n);
    }

    private static int readN(String[] args) {
        if (args.length > 0) {
            return Integer.parseInt(args[0]);
        }

        System.out.print("Enter n: ");
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        }
    }
}
