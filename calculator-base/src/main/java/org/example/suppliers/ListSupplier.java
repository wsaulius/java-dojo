package org.example.suppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ListSupplier<T> implements Supplier<List<T>> {

    private final Supplier<T> elementSupplier;
    private final int size;

    public ListSupplier(Supplier<T> elementSupplier, int size) {
        if (elementSupplier == null) {
            throw new IllegalArgumentException("elementSupplier must not be null");
        }
        if (size < 0) {
            throw new IllegalArgumentException("size must be >= 0");
        }
        this.elementSupplier = elementSupplier;
        this.size = size;
    }

    @Override
    public List<T> get() {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(elementSupplier.get());
        }
        return list;
    }
}