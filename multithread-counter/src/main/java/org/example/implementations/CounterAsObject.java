package org.example.implementations;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Semaphore;

// Object
public class CounterAsObject {

    private String tag;
    protected int count = 0;

    private final Object lock = new Object();

    // Explicitly allow race conditioned resource to be accessed by 2 threads. FIFO order
    private final Semaphore semaphore = new Semaphore( 2, true );

    // Special instance of counter for multithreaded
    public AtomicInteger countAtomic = new AtomicInteger(0);

    // Option 1
    public synchronized void increment( final String thread ) {

        count++;
        this.tag = thread;

        System.out.println("Inc count: " + count + " by " + this.tagged() );
        this.tag = "clean";
    }

    // Option 2
    public void incrementAsLockedThis( final String thread ) {

        // Lock an CounterAsObject as object
        synchronized ( this ) {

            count++;
            this.tag = thread;

            System.out.println("Inc count: " + count + " by " + this.tagged());
            this.tag = "clean";

        }
    }

    // Option 3
    public void incrementAsLockedObject( final String thread ) {

        // Lock an Object as lock
        synchronized ( lock ) {

            count++;
            this.tag = thread;

            System.out.println("Inc count: " + count + " by " + this.tagged());
            this.tag = "clean";

        }
    }

    // Option 4
    public void incrementAsAtomicCounter( final String thread ) {

            countAtomic.incrementAndGet();
            this.tag = thread;

            System.out.println("Inc count atomic: " + countAtomic + " by " + this.tagged());
            this.tag = "clean";

    }

    // Option 5
    public void incrementAsSemaphore( final String thread ) throws java.lang.InterruptedException {

        System.out.println( "Acquire? " + semaphore.tryAcquire() );
        try {
            count++;
            this.tag = thread;

        } finally {

            semaphore.release();
        }

        System.out.println("Inc count sem: " + this.counter() + " by " + this.tagged());
        this.tag = "clean";

    }

    public int counter() {
        return count;
    }

    public String tagged() {
        return tag;
    }

};
