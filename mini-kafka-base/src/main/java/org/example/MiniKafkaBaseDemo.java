package org.example;

import org.example.consumer.Consumer;
import org.example.models.Broker;
import org.example.models.Message;

public class MiniKafkaBaseDemo {
    public static void main(String[] args) {
        Broker broker = new Broker();
        Consumer consumer = new Consumer();

        broker.createTopic("orders");

        broker.publish("orders", new Message("Order 1"));
        broker.publish("orders", new Message("Order 2"));

        consumer.consume(broker.getTopic("orders"));
        consumer.consume(broker.getTopic("orders"));
    }
}