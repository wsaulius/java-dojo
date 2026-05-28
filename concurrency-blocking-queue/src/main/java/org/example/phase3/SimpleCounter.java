package org.example.phase3;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCounter {
    AtomicInteger count = new AtomicInteger(0);

    void increment() {
        count.incrementAndGet();
    }
}
