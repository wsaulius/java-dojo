package org.example.consumer;

import org.example.models.Message;
import org.example.models.Topic;


public class Consumer {

    private final Topic topic;

    public Consumer(Topic topic) {
        this.topic = topic;
    }

    public void start() {
        while (true) {
            Message message = topic.getMessage();

            if (message != null) {
                System.out.println(Thread.currentThread().getName() + " processed " + message.getPayload());
            }
        }
    }

}

