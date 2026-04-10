package org.example.suppliers;

import java.util.function.Supplier;

/**
 * Supplies the same constant value on every call.
 *
 * @param <T> supplied value type
 */
class ConstantSupplier<T> implements Supplier<T> {

    private final T value;

    /**
     * Creates a supplier that always returns the given value.
     *
     * @param value constant value to return
     */
    public ConstantSupplier(T value) {
        this.value = value;
    }

    /**
     * Returns the configured constant value.
     *
     * @return constant value
     */
    @Override
    public T get() {
        return value;
    }
}