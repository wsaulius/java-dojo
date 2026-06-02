package org.example.phase4locks.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

    private Queue<String> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();

    public void produce(String message) {

        lock.lock();

        try {

            queue.add(message);
            System.out.println("Produced: " + message);
            notEmpty.signal();

        } finally {

            lock.unlock();

        }
    }

    public String consume() throws InterruptedException {

        lock.lock();

        try {

            while (queue.isEmpty()) {
                System.out.println("Queue is empty, waiting...");
                notEmpty.await();
            }

            return queue.remove();

        } finally {
            lock.unlock();
        }
    }
}
