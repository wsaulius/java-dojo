package org.example.phase3;


public class SharedCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        SimpleCounter counter = new SimpleCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected value 20000. \nFinal counter value with AtomicInteger: " + counter.count.get());

    }
}
