package org.example.suppliers;

import java.util.function.Supplier;

/**
 * Supplies incrementing integer values starting from a given initial value.
 */
class IncrementingSupplier implements Supplier<Integer> {

    private int current;

    /**
     * Creates an incrementing supplier with the given start value.
     *
     * @param start initial value to return on the first call
     */
    public IncrementingSupplier(int start) {
        this.current = start;
    }

    /**
     * Returns the current value and then increments it.
     *
     * @return current integer value
     */
    @Override
    public Integer get() {
        return current++;
    }
}