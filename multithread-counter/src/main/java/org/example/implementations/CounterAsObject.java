package org.example.implementations;

// Object
public class CounterAsObject {

    protected int count = 0;

    public void increment( final String thread ) {

        count++;


        System.out.println("Inc count: " + count + " by " + thread );
    }

    public int counter() {

        return count;
    }
};
