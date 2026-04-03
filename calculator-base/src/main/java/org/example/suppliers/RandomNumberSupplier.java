package org.example.suppliers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class RandomNumberSupplier implements Supplier<Integer> {

    private final int min;
    private final int max;

    public RandomNumberSupplier(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer get() {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}