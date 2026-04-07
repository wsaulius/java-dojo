package org.example.suppliers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

class BooleanRandomSupplier implements Supplier<Boolean> {

    @Override
    public Boolean get() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}