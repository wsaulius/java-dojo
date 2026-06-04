package org.example.producer;

import org.example.models.Broker;
import org.example.models.Message;

public class Producer {
    private final Broker broker;

    public Producer(Broker broker) {
        this.broker = broker;
    }

    public void send(String topicName, String payload) {
        broker.publish(topicName,new Message(payload));
    }
}
