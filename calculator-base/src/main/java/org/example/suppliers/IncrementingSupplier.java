package org.example.suppliers;

import java.util.function.Supplier;

class IncrementingSupplier implements Supplier<Integer> {

    private int current;

    public IncrementingSupplier(int start) {
        this.current = start;
    }

    @Override
    public Integer get() {
        return current++;
    }
}