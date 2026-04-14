package org.example.suppliers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomNumberSupplierTest {

    @Test
    void constructorShouldThrowWhenMinIsGreaterThanMax() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RandomNumberSupplier(5, 4)
        );

        assertEquals("min must be <= max", exception.getMessage());
    }

    @Test
    void getShouldAlwaysReturnSameValueWhenMinEqualsMax() {
        RandomNumberSupplier supplier = new RandomNumberSupplier(7, 7);

        assertEquals(7, supplier.get());
        assertEquals(7, supplier.get());
        assertEquals(7, supplier.get());
    }

    @Test
    void getShouldAlwaysReturnValueWithinInclusiveRange() {
        RandomNumberSupplier supplier = new RandomNumberSupplier(3, 8);

        for (int i = 0; i < 1000; i++) {
            int value = supplier.get();
            assertTrue(value >= 3 && value <= 8,
                    "Generated value out of range: " + value);
        }
    }
}