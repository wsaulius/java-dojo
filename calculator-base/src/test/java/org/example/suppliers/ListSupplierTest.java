package org.example.suppliers;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ListSupplierTest {

    @Test
    void constructorShouldThrowWhenElementSupplierIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ListSupplier<>(null, 3)
        );

        assertEquals("elementSupplier must not be null", exception.getMessage());
    }

    @Test
    void constructorShouldThrowWhenSizeIsNegative() {
        Supplier<Integer> supplier = () -> 1;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ListSupplier<>(supplier, -1)
        );

        assertEquals("size must be >= 0", exception.getMessage());
    }

    @Test
    void getShouldReturnEmptyListWhenSizeIsZero() {
        AtomicInteger calls = new AtomicInteger();
        ListSupplier<Integer> supplier = new ListSupplier<>(calls::getAndIncrement, 0);

        List<Integer> result = supplier.get();

        assertTrue(result.isEmpty());
        assertEquals(0, calls.get());
    }

    @Test
    void getShouldFillListUsingElementSupplier() {
        AtomicInteger counter = new AtomicInteger(1);
        ListSupplier<Integer> supplier = new ListSupplier<>(counter::getAndIncrement, 4);

        List<Integer> result = supplier.get();

        assertEquals(List.of(1, 2, 3, 4), result);
        assertEquals(5, counter.get());
    }

    @Test
    void getShouldReturnNewListInstanceOnEachCall() {
        AtomicInteger counter = new AtomicInteger(1);
        ListSupplier<Integer> supplier = new ListSupplier<>(counter::getAndIncrement, 2);

        List<Integer> first = supplier.get();
        List<Integer> second = supplier.get();

        assertNotSame(first, second);
        assertEquals(List.of(1, 2), first);
        assertEquals(List.of(3, 4), second);
    }
}