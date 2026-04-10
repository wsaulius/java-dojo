package org.example.suppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Supplies lists populated by repeatedly calling an element supplier.
 *
 * @param <T> list element type
 */
public class ListSupplier<T> implements Supplier<List<T>> {

    private final Supplier<T> elementSupplier;
    private final int size;

    /**
     * Creates a list supplier with the given element supplier and target size.
     *
     * @param elementSupplier supplier used to generate each list element
     * @param size number of elements to generate
     * @throws IllegalArgumentException if the element supplier is null or the size is negative
     */
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

    /**
     * Returns a new list filled with generated elements.
     *
     * @return generated list
     */
    @Override
    public List<T> get() {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(elementSupplier.get());
        }
        return list;
    }
}