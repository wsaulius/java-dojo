package org.example;

import org.example.consumer.Consumer;
import org.example.models.Broker;
import org.example.models.Message;
import org.example.models.Topic;

public class MiniKafkaBaseDemo {
    public static void main(String[] args) throws InterruptedException {
        Broker broker = new Broker();

        broker.createTopic("orders");

        Topic orders = broker.getTopic("orders");

        Consumer consumer = new Consumer(orders);

        Thread consumerThread = new Thread(consumer::start);

        consumerThread.start();

        Producer producer = new Producer(broker);

        producer.send("orders", "Order 1");
        producer.send("orders", "Order 2");
        producer.send("orders", "Order 3");
    }
}