package org.example;

import org.example.consumer.Consumer;
import org.example.helpers.OffsetManager;
import org.example.models.Broker;
import org.example.models.Message;
import org.example.models.Topic;
import org.example.producer.Producer;

public class MiniKafkaBaseDemo {
    public static void main(String[] args) throws InterruptedException {
        Broker broker = new Broker();

        broker.createTopic("orders");


        broker.publish("orders", new Message("Order 1"));
        broker.publish("orders", new Message("Order 2"));

        Topic topic = broker.getTopic("orders");
        OffsetManager offsetManager = new OffsetManager();

        Consumer consumerA = new Consumer("consumerA", topic, offsetManager);
        Consumer consumerB = new Consumer("consumerB", topic, offsetManager);

        consumerA.poll();
        consumerA.poll();

        consumerB.poll();
        consumerB.poll();
    }
}