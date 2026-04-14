package org.example.suppliers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CyclingSupplierTest {

    @Test
    void constructorShouldThrowWhenValuesAreNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new CyclingSupplier<>(null)
        );

        assertEquals("values must not be null or empty", exception.getMessage());
    }

    @Test
    void constructorShouldThrowWhenValuesAreEmpty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new CyclingSupplier<>(List.of())
        );

        assertEquals("values must not be null or empty", exception.getMessage());
    }

    @Test
    void getShouldCycleThroughValuesAndWrapToBeginning() {
        CyclingSupplier<String> supplier = new CyclingSupplier<>(List.of("A", "B", "C"));

        assertEquals("A", supplier.get());
        assertEquals("B", supplier.get());
        assertEquals("C", supplier.get());
        assertEquals("A", supplier.get());
        assertEquals("B", supplier.get());
    }

    @Test
    void getShouldKeepReturningSingleValueForSingleElementList() {
        CyclingSupplier<Integer> supplier = new CyclingSupplier<>(List.of(7));

        assertEquals(7, supplier.get());
        assertEquals(7, supplier.get());
        assertEquals(7, supplier.get());
    }
}