package org.example.suppliers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Supplies random boolean values.
 */
class BooleanRandomSupplier implements Supplier<Boolean> {

    /**
     * Returns a random boolean value.
     *
     * @return random boolean value
     */
    @Override
    public Boolean get() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}