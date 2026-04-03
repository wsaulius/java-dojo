package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class PrimeCheckOperation implements UnaryOperation<Integer, Boolean> {
    @Override
    public Boolean apply(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("IS_PRIME input cannot be null");
        }
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