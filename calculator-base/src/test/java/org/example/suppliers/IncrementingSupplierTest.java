package org.example.suppliers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncrementingSupplierTest {

    @Test
    void getShouldReturnStartValueOnFirstCall() {
        IncrementingSupplier supplier = new IncrementingSupplier(10);

        assertEquals(10, supplier.get());
    }

    @Test
    void getShouldIncrementOnEachCall() {
        IncrementingSupplier supplier = new IncrementingSupplier(-2);

        assertEquals(-2, supplier.get());
        assertEquals(-1, supplier.get());
        assertEquals(0, supplier.get());
        assertEquals(1, supplier.get());
    }
}