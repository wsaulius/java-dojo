package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.math.BigInteger;
import java.util.function.Function;

@Singleton
public final class FibonacciOperation implements Function<Integer, BigInteger> {

    @Override
    public BigInteger apply(Integer n) {
        return compute(n);
    }

    private BigInteger compute(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative not allowed");
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger next = a.add(b);
            a = b;
            b = next;
        }

        return b;
    }
}