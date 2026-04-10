package org.example.suppliers;

import java.util.List;
import java.util.function.Supplier;

/**
 * Supplies values by cycling through a fixed list repeatedly.
 *
 * @param <T> supplied value type
 */
class CyclingSupplier<T> implements Supplier<T> {

    private final List<T> values;
    private int index;

    /**
     * Creates a cycling supplier from the given list of values.
     *
     * @param values source values to cycle through
     * @throws IllegalArgumentException if the list is null or empty
     */
    public CyclingSupplier(List<T> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("values must not be null or empty");
        }
        this.values = values;
        this.index = 0;
    }

    /**
     * Returns the next value in the cycle.
     *
     * @return next cycled value
     */
    @Override
    public T get() {
        T value = values.get(index);
        index = (index + 1) % values.size();
        return value;
    }
}