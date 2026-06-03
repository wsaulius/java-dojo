package org.example.consumer;

import org.example.models.Message;
import org.example.models.Topic;

public class Consumer {

    public void consume(Topic topic){
        for(Message message: topic.getMessages()) {
            System.out.println("Consumed: " + message.getPayload());
        }
    }
}
