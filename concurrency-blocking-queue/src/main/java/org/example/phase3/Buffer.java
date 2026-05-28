package org.example.phase3;

public class Buffer {

    private int data;
    private boolean hasData = false;

    public synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait();
        }

        data = value;
        hasData = true;

        System.out.println("Produced: " + value);

        notify();
    }

    public synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait();
        }

        int value = data;
        hasData = false;

        System.out.println("Consumed: " + value);

        notify();

        return value;
    }
}
