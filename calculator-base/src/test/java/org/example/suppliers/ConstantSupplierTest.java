package org.example.suppliers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantSupplierTest {

    @Test
    void getShouldAlwaysReturnSameValue() {
        ConstantSupplier<String> supplier = new ConstantSupplier<>("fixed");

        assertEquals("fixed", supplier.get());
        assertEquals("fixed", supplier.get());
        assertEquals("fixed", supplier.get());
    }

    @Test
    void getShouldReturnNullWhenConstructedWithNull() {
        ConstantSupplier<String> supplier = new ConstantSupplier<>(null);

        assertNull(supplier.get());
        assertNull(supplier.get());
    }
}