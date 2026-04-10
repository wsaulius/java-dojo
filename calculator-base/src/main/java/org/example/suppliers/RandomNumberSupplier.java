package org.example.suppliers;

import java.util.function.Supplier;

/**
 * Supplies random integer values within an inclusive range.
 */
public class RandomNumberSupplier implements Supplier<Integer> {

    private final int min;
    private final int max;

    /**
     * Creates a random number supplier for the given inclusive range.
     *
     * @param min minimum generated value
     * @param max maximum generated value
     * @throws IllegalArgumentException if min is greater than max
     */
    public RandomNumberSupplier(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * Returns a random integer within the configured inclusive range.
     *
     * @return random integer value
     */
    @Override
    public Integer get() {
        return java.util.concurrent.ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}