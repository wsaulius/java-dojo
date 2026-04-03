package org.example.implementations;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

// Object
public class ReentrantCounter {

    private String tag;
    protected int count = 0;

    private final ReentrantLock lock = new ReentrantLock();

    // Special instance of counter for multithreaded
    public AtomicInteger countAtomic = new AtomicInteger(0);

    // Good example of a thread released after the exception
    public synchronized void increment( final String thread ) throws InterruptedException {

        lock.tryLock(1, TimeUnit.SECONDS);
        try {
            count++;
            this.tag = thread;
            System.out.println("Inc count: " + this.counter() + " by " + this.tagged() );
            throw new Exception(" dsfs");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    // Option 2
    public void incrementAsLockedThis( final String thread ) {

        // Lock an CounterAsObject as object
        synchronized ( this ) {

            count++;
            this.tag = thread;

            System.out.println("Inc count: " + this.counter() + " by " + this.tagged());
            this.tag = "clean";

        }
    }

    // Option 3
    public void incrementAsLockedObject( final String thread ) {

        // Lock an Object as lock
        synchronized ( lock ) {

            count++;
            this.tag = thread;

            System.out.println("Inc count: " + this.counter() + " by " + this.tagged());
            this.tag = "clean";

        }
    }

    // Option 4
    public void incrementAsAtomicCounter( final String thread ) {

            countAtomic.incrementAndGet();
            this.tag = thread;

            System.out.println("Inc count atomic: " + this.counter() + " by " + this.tagged());
            this.tag = "clean";

    }

    public int counter() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public String tagged() {
        return tag;
    }

};
