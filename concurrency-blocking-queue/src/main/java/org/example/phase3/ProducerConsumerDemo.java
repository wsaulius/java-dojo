package org.example.phase3;

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(1000); //just for show , everything would happen to quickly to see for humans
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(1000); //just for show , everything would happen to quickly to see for humans
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
