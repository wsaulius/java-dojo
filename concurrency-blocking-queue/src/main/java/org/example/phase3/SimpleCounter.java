package org.example.phase3;

public class SimpleCounter {
     int count = 0;

     synchronized void increment() {
         count++;
     }
}
