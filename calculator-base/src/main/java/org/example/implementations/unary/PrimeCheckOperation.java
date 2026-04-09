package org.example.implementations.unary;

import jakarta.inject.Singleton;

import java.util.function.IntPredicate;

@Singleton
public class PrimeCheckOperation implements IntPredicate {

    @Override
    public boolean test(int n) {

        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        int limit = (int) Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}