package org.example;

import org.example.implementations.CounterAsObject;

public class MultithreadedRunner {

    public static void main(String[] args) throws InterruptedException {

            Thread t1, t2;
            CounterAsObject counter = new CounterAsObject();

            t1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) counter.increment( "t1" );
            });

            t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) counter.increment( "t2" );
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println( counter.counter() );
    }
}
