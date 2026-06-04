package org.example.consumer;

import org.example.helpers.OffsetManager;
import org.example.models.Message;
import org.example.models.Topic;


public class Consumer {

    private final String consumerId;
    private final Topic topic;
    private final OffsetManager offsetManager;

    public Consumer(String consumerId, Topic topic, OffsetManager offsetManager) {
        this.consumerId = consumerId;
        this.topic = topic;
        this.offsetManager = offsetManager;
    }

    public void poll() {
        int offset = offsetManager.getOffset(consumerId);
        Message message = topic.getMessage(offset);

        if (message == null) {
            return;
        }

        System.out.println(consumerId + " consumed: " + message.getPayload());

        offsetManager.commitOffset(consumerId, offset + 1);
    }

}

