package org.example.implementations;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

// Object
public class ReentrantCounter {

    private String tag;
    protected int count = 0;

    private final ReentrantLock lock = new ReentrantLock();

    // Special instance of counter for multithreaded
    public AtomicInteger countAtomic = new AtomicInteger(0);

    // Option 1
    public synchronized void increment( final String thread ) {

        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }

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


    public int counter() {
        return count;
    }

    public String tagged() {
        return tag;
    }

};
