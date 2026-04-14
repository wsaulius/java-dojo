package org.example.suppliers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanRandomSupplierTest {

    @Test
    void getShouldReturnNonNullBoolean() {
        BooleanRandomSupplier supplier = new BooleanRandomSupplier();

        Boolean value = supplier.get();

        assertNotNull(value);
    }
}