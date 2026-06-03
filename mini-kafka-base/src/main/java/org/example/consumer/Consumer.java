package org.example.consumer;

import org.example.models.Message;
import org.example.models.Topic;

import java.util.List;

public class Consumer {

    private int currentOffset = 0;

    public void consume(Topic topic) {
        List<Message> messages = topic.getMessages();

        while(currentOffset < messages.size()) {
            Message message = messages.get(currentOffset);
            System.out.println("Consumed: " + message.getPayload());
            currentOffset++;
        }
    }
}

