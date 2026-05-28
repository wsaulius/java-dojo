package org.example.phase3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i <= 5; i++) {
                    queue.put(i); //automatically blocks if queue is full
                    System.out.println("Producing: " + i);
                    Thread.sleep(1000); //just for show , everything would happen to quickly to see for humans
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i <= 5; i++) {
                    int value = queue.take(); // automatically blocks if queue is empty
                    System.out.println("Consumed: " + value);
                    Thread.sleep(1000); //just for show , everything would happen to quickly to see for humans
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();
    }
}
