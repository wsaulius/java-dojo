package org.example.suppliers;

import java.util.List;
import java.util.function.Supplier;

class CyclingSupplier<T> implements Supplier<T> {

    private final List<T> values;
    private int index;

    public CyclingSupplier(List<T> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("values must not be null or empty");
        }
        this.values = values;
        this.index = 0;
    }

    @Override
    public T get() {
        T value = values.get(index);
        index = (index + 1) % values.size();
        return value;
    }
}